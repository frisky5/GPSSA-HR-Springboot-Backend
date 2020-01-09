package com.gpssahistoricalreport.historicalreporting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonEntities.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/")
public class Controller {
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/summaryReport")
    public String getSummaryReportData(@RequestBody String payload) {
        //JSON request String to Object
        ObjectMapper objectMapper = new ObjectMapper();
        SummaryReportJsonRequest jsonRequest = new SummaryReportJsonRequest();
        try {
            jsonRequest = objectMapper.readValue(payload, SummaryReportJsonRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String startTime = "";
        String endTime = "";
        String startDateTime = jsonRequest.getStartDateTime();
        String endDateTime = jsonRequest.getEndDateTime();
        String jsonResponseString = "";
        SummaryReportJsonResponse jsonResponseObject = new SummaryReportJsonResponse();

        //Initialize Variables to be filled with MSSQL Queries results
        String callsAfterWorkingHours = "";
        String mostFrequentNumber = "";
        String numberOfIvrCalls = "";
        String maxTalkTime = "";
        String numberOfComplainCalls = "";
        String minimumWaitTime = "";
        String minimumTalkTime = "";
        String numberOfAbandonedCalls = "";
        String maximumWaitingTime = "";

        //MSSQL Queries
        String callsAfterWorkingHoursSql = "select COUNT(*) from dbo.CALLSDetails where (CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') AND (SKILLSET <>'NULL') AND ((cast(CALLDATETIME1 as time) between '14:30:00' AND '15:30:0')) AND ((FINALDISPOSITION = 'Answered') OR(FINALDISPOSITION = 'Abandoned')OR(FINALDISPOSITION = 'Connected'))";
        String mostFrequentNumberSql = "select top 1 CALLINGNUMBER, COUNT(CALLINGNUMBER) as dupcount, FINALDISPOSITION from dbo.CALLSDetails where (CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') and (LEN(CALLINGNUMBER) > 5) and (ISNUMERIC(CALLINGNUMBER) = 1) and (FINALDISPOSITION = 'Answered') group by CALLINGNUMBER, FINALDISPOSITION having count(*) > 0 order by dupcount desc";
        String numberOfIvrCallsSql = "select count(*) from dbo.CALLSDetails where (CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') and (IVROnly = 1)";
        String maxTalkTimeSql = "select max (TALKTIME) from dbo.CALLSDetails where (CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') AND (SKILLSET <>'NULL')";
        String numberOfComplainCallsSql = "select count(*) from dbo.CALLSDetails where SKILLSET like '%compl%' and (CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "')";
        String minimumWaitTimeSql = "select min (QUEUETIME) from dbo.CALLSDetails where (CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "')";
        String minimumTalkTimeSql = "select min (TALKTIME) as mintalktime from dbo.CALLSDetails where (CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') ";
        String numberOfAbandonedCallsSql = "SELECT count(*) FROM dbo.CALLSDetails where FINALDISPOSITION like 'abandoned' AND (CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') AND (SKILLSET is not null AND SKILLSET <> 'NULL' )";
        String maximumWaitingTimeSql = "select MAX (QUEUETIME) from CALLSDetails where(CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "') AND (LEN(CALLINGNUMBER) > 5)";
        String maximumNumberOfWaitingCalls = "select Max(Callswaiting_ar) + Max(CallsWaiting_En) from dbo.CALLSWaiting where (IntervalTime BETWEEN '" + startDateTime + "' AND '" + endDateTime + "')";
        String totalNumberOfAllCalls = "select count(*) from dbo.CALLSDetails where (SKILLSET<> 'NULL')  AND(CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "')";
        String totalNumberOfGeneralArabic = "select count(*) from dbo.CALLSDetails where LEN(CALLINGNUMBER) > 5 AND isnumeric(CALLINGNUMBER)= 1 AND((CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "')) AND SKILLSET = 'General Arabic'";
        String totalNumberOfGeneralEnglish = "select count(*) from dbo.CALLSDetails where LEN(CALLINGNUMBER) > 5 AND isnumeric(CALLINGNUMBER)= 1 AND((CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "')) AND SKILLSET = 'General English'";
        String totalNumberOfComplaintsArabic = "select count(*) from dbo.CALLSDetails where LEN(CALLINGNUMBER) > 5 AND isnumeric(CALLINGNUMBER)= 1 AND((CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "')) AND SKILLSET = 'Complaints Arabic'";
        String totalNumberOfComplaintsEnglish = "select count(*) from dbo.CALLSDetails where LEN(CALLINGNUMBER) > 5 AND isnumeric(CALLINGNUMBER)= 1 AND((CALLDATETIME1 BETWEEN '" + startDateTime + "' AND '" + endDateTime + "')) AND SKILLSET = 'Complaints English'";

        //MSSQL Connection Props
        String connectionUrl = "jdbc:sqlserver://10.10.60.61:1433;databaseName=CustomDB;user=sa;password=Avaya123$";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        //get values from MSSQL DB
        try {
            boolean flag = false;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(maximumNumberOfWaitingCalls);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setmaximumNumberOfWaitingCalls(resultSet.getString(1).trim());
                    }
                }
            }
            //get Calls After Working Hours nunber
            resultSet = statement.executeQuery(callsAfterWorkingHoursSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {

                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setCallsAfterWorkingHours(resultSet.getString(1).trim());
                    }
                }
            }

            //get most frequent number
            resultSet = statement.executeQuery(mostFrequentNumberSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setMostFrequentNumber(resultSet.getString(1).trim());
                    }
                }
            }

            //get number of IVR Calls
            resultSet = statement.executeQuery(numberOfIvrCallsSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setNumberOfIvrCalls(resultSet.getString(1).trim());
                    }
                }
            }

            //get maximum talk time
            resultSet = statement.executeQuery(maxTalkTimeSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setMaxTalkTime(resultSet.getString(1).trim());
                    }
                }
            }

            //get number of complain calls
            resultSet = statement.executeQuery(numberOfComplainCallsSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setNumberOfComplainCalls(resultSet.getString(1).trim());
                    }
                }
            }

            //get minimum wait time
            resultSet = statement.executeQuery(minimumWaitTimeSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setMinimumWaitTime(resultSet.getString(1).trim());
                    }
                }
            }

            //get minimum Talk time
            resultSet = statement.executeQuery(minimumTalkTimeSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setMinimumTalkTime(resultSet.getString(1).trim());
                    }
                }
            }

            //get number of abandoned calls
            resultSet = statement.executeQuery(numberOfAbandonedCallsSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setNumberOfAbandonedCalls(resultSet.getString(1).trim());
                    }
                }
            }

            //get maximum waiting time
            resultSet = statement.executeQuery(maximumWaitingTimeSql);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setMaximumWaitingTime(resultSet.getString(1).trim());
                    }
                }
            }

            //Total number of all calls
            resultSet = statement.executeQuery(totalNumberOfAllCalls);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setTotalNumberOfAllCalls(resultSet.getString(1).trim());
                    }
                }
            }

            //Total number General Arabic
            resultSet = statement.executeQuery(totalNumberOfGeneralArabic);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setTotalCallsOfGeneralArabic(resultSet.getString(1).trim());
                    }
                }
            }

            //Total number General English
            resultSet = statement.executeQuery(totalNumberOfGeneralEnglish);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setTotalCallsOfGeneralEnglish(resultSet.getString(1).trim());
                    }
                }
            }

            //Total number Complaints Arabic
            resultSet = statement.executeQuery(totalNumberOfComplaintsArabic);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setTotalCallsOfComplaintsArabic(resultSet.getString(1).trim());
                    }
                }
            }

            //Total number Complaints English
            resultSet = statement.executeQuery(totalNumberOfComplaintsEnglish);
            if (resultSet.next() != false) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    if (resultSet.getObject(1) != null) {
                        flag = true;
                        jsonResponseObject.setTotalCallsOfComplaintsEnglish(resultSet.getString(1).trim());
                    }
                }
            }
            jsonResponseString = objectMapper.writeValueAsString(jsonResponseObject);
            connection.close();
            System.out.println(payload);
            System.out.println(jsonResponseString);
            return jsonResponseString;
        } catch (
                Exception e) {
            e.printStackTrace();
        }

        return jsonResponseString;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/callsDetailsReport")
    public String getCallsDetails(@RequestBody String payload) {
        boolean ivrCallsFlag = false;
        boolean normalCallFlag = false;
        boolean answeredCallsFlag = false;
        boolean abandonedCallsFlag = false;

        if (payload.isEmpty()) {
            return "[{agentid: \"NULL\"\n" +
                    "calldatetime: \"NULL\"\n" +
                    "callid: \"NULL\"\n" +
                    "callingnumber: \"NULL\"\n" +
                    "finaldisposition: \"NULL\"\n" +
                    "queuetime: \"NULL\"\n" +
                    "ringtime: \"NULL\"\n" +
                    "skillset: \"NULL\"}]";
        }
        //JSON request String to Object
        ObjectMapper objectMapper = new ObjectMapper();
        CallsDetailsJsonRequest jsonRequest = new CallsDetailsJsonRequest();

        try {
            jsonRequest = objectMapper.readValue(payload, CallsDetailsJsonRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String startTime = "";
        String endTime = "";
        String startDateTime = jsonRequest.getStartDateTime();
        String endDateTime = jsonRequest.getEndDateTime();
        List<String> finalDisposition = jsonRequest.getFinalDisposition();
        List<String> callType = jsonRequest.getCalltype();
        List<String> skills = jsonRequest.getSkillset();
        String jsonResponseString = "";

        if (finalDisposition.size() <= 0 || callType.size() <= 0 || skills.size() <= 0) {
            return "[{agentid: \"NULL\"\n" +
                    "calldatetime: \"NULL\"\n" +
                    "callid: \"NULL\"\n" +
                    "callingnumber: \"NULL\"\n" +
                    "finaldisposition: \"NULL\"\n" +
                    "queuetime: \"NULL\"\n" +
                    "ringtime: \"NULL\"\n" +
                    "skillset: \"NULL\"}]";
        }
        for (int looper = 0; looper < finalDisposition.size(); looper++) {
            if (finalDisposition.get(looper).equals("answered")) {
                answeredCallsFlag = true;
            } else if (finalDisposition.get(looper).equals("abandoned")) {
                abandonedCallsFlag = true;
            }
        }
        for (int looper = 0; looper < callType.size(); looper++) {
            if (callType.get(looper).equals("ivr")) {
                ivrCallsFlag = true;
            } else if (callType.get(looper).equals("call")) {
                normalCallFlag = true;
            }
        }
        String connectionUrl = "jdbc:sqlserver://10.10.60.61:1433;databaseName=CustomDB;user=sa;password=Avaya123$";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String callsDetailsSql = "select distinct CALLID, AGENTID, CALLDATETIME1, CALLINGNUMBER, RINGTIME, SKILLSET, FINALDISPOSITION, QUEUETIME as QUEUETIME from dbo.CALLSDetails where ((SKILLSET <> 'NULL') AND (";
        for (int looper = 0; looper < skills.size(); looper++) {
            if (looper == skills.size() - 1)
                callsDetailsSql += "(SKILLSET = '" + skills.get(looper) + "')))";
            else
                callsDetailsSql += "(SKILLSET = '" + skills.get(looper) + "') OR";
        }

        if (answeredCallsFlag && abandonedCallsFlag) {
            callsDetailsSql += "AND ((FINALDISPOSITION like 'answered')or (FINALDISPOSITION like 'abandoned')) AND (CALLDATETIME1 between '" + startDateTime + "' and '" + endDateTime + "') ORDER BY CALLDATETIME1 desc";
        } else if (answeredCallsFlag) {
            callsDetailsSql += "AND ((FINALDISPOSITION like 'answered')) AND (CALLDATETIME1 between '" + startDateTime + "' and '" + endDateTime + "') ORDER BY CALLDATETIME1 desc";
        } else if (abandonedCallsFlag) {
            callsDetailsSql += " AND ((FINALDISPOSITION like 'abandoned')) AND (CALLDATETIME1 between '" + startDateTime + "' and '" + endDateTime + "') ORDER BY CALLDATETIME1 desc";
        }

        if (ivrCallsFlag && !normalCallFlag) {
            callsDetailsSql = "select distinct CALLID, AGENTID, CALLDATETIME1, CALLINGNUMBER, RINGTIME, SKILLSET, FINALDISPOSITION, QUEUETIME as QUEUETIME, IVROnly from dbo.CALLSDetails where(IVROnly = 1) AND (CALLDATETIME1 between '" + startDateTime + "' and '" + endDateTime + "') ORDER BY CALLDATETIME1 desc";
        }
        System.out.println(callsDetailsSql);
        //get values from MSSQL DB
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //get Calls After Working Hours nunber
            resultSet = statement.executeQuery(callsDetailsSql);
            resultSet.last();
            int rowsCount = resultSet.getRow();
            resultSet.first();
            CallsDetailsJsonResponse jsonResponseObject[] = new CallsDetailsJsonResponse[rowsCount];
            if (rowsCount <= 0) {
                return "[{}]";
            }
            int arrayIndex = 0;
            do {
                jsonResponseObject[arrayIndex] = new CallsDetailsJsonResponse();
                jsonResponseObject[arrayIndex].setCallid(resultSet.getString(1).trim());
                jsonResponseObject[arrayIndex].setAgentid(resultSet.getString(2).trim());
                jsonResponseObject[arrayIndex].setCalldatetime(resultSet.getString(3).trim());
                jsonResponseObject[arrayIndex].setCallingnumber(resultSet.getString(4).trim());
                if (ivrCallsFlag && !normalCallFlag)
                    jsonResponseObject[arrayIndex].setRingtime("0");
                else
                    jsonResponseObject[arrayIndex].setRingtime(resultSet.getString(5).trim());
                jsonResponseObject[arrayIndex].setSkillset(resultSet.getString(6).trim());
                jsonResponseObject[arrayIndex].setFinaldisposition(resultSet.getString(7).trim());
                if (ivrCallsFlag && !normalCallFlag)
                    jsonResponseObject[arrayIndex].setQueuetime("0");
                else
                    jsonResponseObject[arrayIndex].setQueuetime(resultSet.getString(8).trim());
                arrayIndex++;
            }
            while (resultSet.next());
            jsonResponseString = objectMapper.writeValueAsString(jsonResponseObject);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }
        return jsonResponseString;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/agentPerformance")
    public String getAgentPerformanceReport(@RequestBody String payload) {
        System.out.println(payload);
        if (payload.isEmpty()) {
            return "[]";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        AgentPerformanceReportJsonRequest jsonRequestObject = new AgentPerformanceReportJsonRequest();

        String jsonRequestString = new String();
        String jsonResponseString = new String();

        try {
            jsonRequestObject = objectMapper.readValue(payload, AgentPerformanceReportJsonRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String ibmConnectionString = "jdbc:informix-sqli://10.10.60.41:50000/cms:INFORMIXSERVER=cms_net";
        String totalTalkTimeAllAgentsQuery = "select sum(talktime), anslogin from root.call_rec where(row_date between \"" + jsonRequestObject.getStartDateTime() + "\" and \"" + jsonRequestObject.getEndDateTime() + "\") and (anslogin is not null) and (";
        for (int i = 0; i < jsonRequestObject.getAgents().size(); i++) {
            if (i == 0)
                totalTalkTimeAllAgentsQuery += " (anslogin = \"" + jsonRequestObject.getAgents().get(i).trim() + "\") ";
            totalTalkTimeAllAgentsQuery += "or (anslogin = \"" + jsonRequestObject.getAgents().get(i).trim() + "\") ";
        }
        totalTalkTimeAllAgentsQuery += " )group by anslogin order by anslogin asc";


        String numberOfCallsAllAgentsQuery = "select count(*),anslogin from( select distinct(seqnum),duration,anslogin from root.call_rec where (row_date between \"" + jsonRequestObject.getStartDateTime() + "\" and \"" + jsonRequestObject.getEndDateTime() + "\") and duration > 0 and (anslogin is not null) and(";
        for (int i = 0; i < jsonRequestObject.getAgents().size(); i++) {
            if (i == 0)
                numberOfCallsAllAgentsQuery += " (anslogin = \"" + jsonRequestObject.getAgents().get(i).trim() + "\") ";
            numberOfCallsAllAgentsQuery += "or (anslogin = \"" + jsonRequestObject.getAgents().get(i).trim() + "\") ";
        }
        numberOfCallsAllAgentsQuery += ") order by seqnum desc) group by anslogin order by anslogin asc";
        String totalAuxTimeAllAgents = "select distinct sum(i_auxtime),logid from root.dagent where logid is not null and(row_date between \"" + jsonRequestObject.getStartDateTime() + "\" and \"" + jsonRequestObject.getEndDateTime() + "\") group by logid order by logid asc";
        String numberOfAbandonedCallsAllAgents = "select sum(abncalls),logid from root.dagent where logid is not null and (row_date between \"" + jsonRequestObject.getStartDateTime() + "\" and \"" + jsonRequestObject.getEndDateTime() + "\") group by logid order by logid asc";
        String totalAcwQuery = "select sum(acwtime), anslogin from root.call_rec where(row_date between \"" + jsonRequestObject.getStartDateTime() + "\" and \"" + jsonRequestObject.getEndDateTime() + "\") and (anslogin is not null) group by anslogin order by anslogin asc";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.informix.jdbc.IfxDriver");
            Connection connection = DriverManager.getConnection(ibmConnectionString, "odbc", "Avaya123$");
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            resultSet = statement.executeQuery(totalTalkTimeAllAgentsQuery);
            resultSet.last();
            int rowsCount = resultSet.getRow();
            resultSet.first();
            AgentPerformanceReportJsonResponse jsonResponseObject[] = new AgentPerformanceReportJsonResponse[rowsCount];
            int arrayIndex = 0;
            if (rowsCount <= 0) {
                return "[{}]";
            }
            do {
                jsonResponseObject[arrayIndex] = new AgentPerformanceReportJsonResponse();
                jsonResponseObject[arrayIndex].setagentID(resultSet.getString(2).trim());
                jsonResponseObject[arrayIndex].setTotalTalkTimeByAgent(resultSet.getString(1).trim());
                arrayIndex++;
            } while (resultSet.next());

            resultSet = statement.executeQuery(numberOfCallsAllAgentsQuery);
            resultSet.last();
            rowsCount = resultSet.getRow();
            resultSet.first();
            arrayIndex = 0;
            if (rowsCount <= 0) {
                return "[{}]";
            }
            do {
                for (int looper = 0; looper < jsonResponseObject.length; looper++) {
                    if (resultSet.getString(2).trim().equals(jsonResponseObject[looper].getagentID())) {
                        jsonResponseObject[looper].setNumberOfCallsReceivedByAgent(resultSet.getString(1).trim());
                    }
                }
                arrayIndex++;
            } while (resultSet.next());

            resultSet = statement.executeQuery(totalAuxTimeAllAgents);
            resultSet.last();
            rowsCount = resultSet.getRow();
            resultSet.first();
            arrayIndex = 0;
            if (rowsCount <= 0) {
                return "[{}]";
            }
            do {
                for (int looper = 0; looper < jsonResponseObject.length; looper++) {
                    if (resultSet.getString(2).trim().equals(jsonResponseObject[looper].getagentID())) {
                        jsonResponseObject[looper].setTotalAuxTimeByAgent(resultSet.getString(1).trim());
                    }
                }
                arrayIndex++;
            } while (resultSet.next());

            resultSet = statement.executeQuery(numberOfAbandonedCallsAllAgents);
            resultSet.last();
            rowsCount = resultSet.getRow();
            resultSet.first();
            arrayIndex = 0;
            if (rowsCount <= 0) {
                return "[{}]";
            }
            do {
                for (int looper = 0; looper < jsonResponseObject.length; looper++) {
                    if (resultSet.getString(2).trim().equals(jsonResponseObject[looper].getagentID())) {
                        jsonResponseObject[looper].setTotalAbandonedCallByAgent(resultSet.getString(1).trim());
                    }
                }
                arrayIndex++;
            } while (resultSet.next());

            resultSet = statement.executeQuery(totalAcwQuery);
            resultSet.last();
            rowsCount = resultSet.getRow();
            resultSet.first();
            arrayIndex = 0;
            if (rowsCount <= 0) {
                return "[{}]";
            }
            do {
                for (int looper = 0; looper < jsonResponseObject.length; looper++) {
                    if (resultSet.getString(2).trim().equals(jsonResponseObject[looper].getagentID())) {
                        jsonResponseObject[looper].setTotalAcwByAgent(resultSet.getString(1).trim());
                    }
                }
                arrayIndex++;
            } while (resultSet.next());
            connection.close();
            jsonResponseString = objectMapper.writeValueAsString(jsonResponseObject);

        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }
        return jsonResponseString;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getSkillsets")
    public String getSkillsets() {
        ObjectMapper objectMapper = new ObjectMapper();
        SkillsetsResponse jsonResponseObject[];
        String connectionUrl = "jdbc:sqlserver://10.10.60.61:1433;databaseName=CustomDB;user=sa;password=Avaya123$";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String getSkillsetsSqlQuery = "select DISTINCT SKILLSET from dbo.CALLSDetails where SKILLSET <>'NULL' order by SKILLSET asc";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //get Calls After Working Hours nunber
            resultSet = statement.executeQuery(getSkillsetsSqlQuery);
            resultSet.last();
            int rowsCount = resultSet.getRow();
            resultSet.beforeFirst();
            jsonResponseObject = new SkillsetsResponse[rowsCount];
            int arrayIndex = 0;
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                jsonResponseObject[arrayIndex] = new SkillsetsResponse();
                jsonResponseObject[arrayIndex].setSkillset(resultSet.getString(1).trim());
                arrayIndex++;
            }
            String jsonResponseString = objectMapper.writeValueAsString(jsonResponseObject);
            return jsonResponseString;
        } catch (Exception e) {
            return "[]";
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAgents")
    public String getAgents() {
        ObjectMapper objectMapper = new ObjectMapper();
        AgentsResponse jsonResponseObject[];
        String getAgentsSqlQuery = "select AgentID,AgentName from dbo.AgentsDetails order by AgentID";
        String connectionUrl = "jdbc:sqlserver://10.10.60.61:1433;databaseName=CustomDB;user=sa;password=Avaya123$";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //get Calls After Working Hours number
            resultSet = statement.executeQuery(getAgentsSqlQuery);
            resultSet.last();
            int rowsCount = resultSet.getRow();
            resultSet.beforeFirst();
            jsonResponseObject = new AgentsResponse[rowsCount];
            int arrayIndex = 0;
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
                jsonResponseObject[arrayIndex] = new AgentsResponse();
                jsonResponseObject[arrayIndex].setAgentId(resultSet.getString(1).trim());
                jsonResponseObject[arrayIndex].setAgentName(resultSet.getString(2).trim());
                arrayIndex++;
            }
            String jsonResponseString = objectMapper.writeValueAsString(jsonResponseObject);
            return jsonResponseString;
        } catch (Exception e) {
            return "[]";
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/getSurveyReport")
    public String getSurveyReport(@RequestBody String payload) {

        boolean inOutDecider = false;
        SurveyReportJsonRequest jsonRequestObject = new SurveyReportJsonRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonRequestObject = objectMapper.readValue(payload, SurveyReportJsonRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
        String connectionUrl = "jdbc:sqlserver://10.10.60.61:1433;databaseName=CustomDB;user=sa;password=Avaya123$";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        InboundSurveyReportJsonResponse inboundSurveyJsonResponseObject[];
        OutboundSurveyReportResponse outboundSurveyJsonResponseObject[];
        String inboundSurveySql = "";
        String inboundSurveyWithAgentSql = "";
        String outboundSurveySql = "select CallDateTime, [Language], CustomerNo, Q1Answer, Q2Answer, Q3Answer, Q4Answer, Q5Answer from dbo.OutboundSurvey where(CallDateTime between '" + jsonRequestObject.getStartDateTime() + "' AND '" + jsonRequestObject.getEndDateTime() + "') ORDER by CallDateTime desc ";
        if (jsonRequestObject.getSurveyType().equals("Inbound")) {
            inOutDecider = true;
            inboundSurveySql = "select CallDateTime, AgentID, CallerNumber, Q1Answer, Q2Answer, Evaluation, [Language] from dbo.InboundSurvey where(CallDateTime between '" + jsonRequestObject.getStartDateTime() + "' AND '" + jsonRequestObject.getEndDateTime() + "') AND (";
            for (int looper = 0; looper < jsonRequestObject.getAgentID().size(); looper++) {
                if (looper == jsonRequestObject.getAgentID().size() - 1)
                    inboundSurveySql += "AgentID = '" + jsonRequestObject.getAgentID().get(looper) + "') ORDER by CallDateTime desc";
                else
                    inboundSurveySql += "AgentID = '" + jsonRequestObject.getAgentID().get(looper) + "' OR ";
            }
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(connectionUrl);
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(inboundSurveySql);
                resultSet.last();
                int rowsCount = resultSet.getRow();
                resultSet.beforeFirst();
                inboundSurveyJsonResponseObject = new InboundSurveyReportJsonResponse[rowsCount];
                int arrayIndex = 0;
                while (resultSet.next()) {
                    inboundSurveyJsonResponseObject[arrayIndex] = new InboundSurveyReportJsonResponse();
                    if (resultSet.getObject(1) != null) {
                        inboundSurveyJsonResponseObject[arrayIndex].setCallDateTime(resultSet.getString(1));
                    }
                    if (resultSet.getObject(2) != null) {
                        inboundSurveyJsonResponseObject[arrayIndex].setAgentID(resultSet.getString(2).trim());
                    }
                    if (resultSet.getObject(3) != null) {
                        inboundSurveyJsonResponseObject[arrayIndex].setCallerNumber(resultSet.getString(3).trim());
                    }
                    if (resultSet.getObject(4) != null) {
                        inboundSurveyJsonResponseObject[arrayIndex].setQ1Answer(resultSet.getString(4).trim());
                    }
                    if (resultSet.getObject(5) != null) {
                        inboundSurveyJsonResponseObject[arrayIndex].setQ2Answer(resultSet.getString(5).trim());
                    }
                    if (resultSet.getObject(6) != null) {
                        inboundSurveyJsonResponseObject[arrayIndex].setEvaluation(resultSet.getString(6).trim());
                    }
                    if (resultSet.getObject(7) != null) {
                        inboundSurveyJsonResponseObject[arrayIndex].setLanguage(resultSet.getString(7).trim());
                    }
                    arrayIndex++;
                }
                connection.close();
                return objectMapper.writeValueAsString(inboundSurveyJsonResponseObject);
            } catch (Exception e) {
                e.printStackTrace();
                return "[]";
            }
        } else if (jsonRequestObject.getSurveyType().equals("Outbound")) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(connectionUrl);
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                resultSet = statement.executeQuery(outboundSurveySql);
                resultSet.last();
                int rowsCount = resultSet.getRow();
                resultSet.beforeFirst();
                outboundSurveyJsonResponseObject = new OutboundSurveyReportResponse[rowsCount];
                int arrayIndex = 0;
                while (resultSet.next()) {
                    outboundSurveyJsonResponseObject[arrayIndex] = new OutboundSurveyReportResponse();
                    if (resultSet.getObject(1) != null) {
                        outboundSurveyJsonResponseObject[arrayIndex].setTimestamp(resultSet.getString(1));
                    }
                    if (resultSet.getObject(2) != null) {
                        outboundSurveyJsonResponseObject[arrayIndex].setLanguage(resultSet.getString(2).trim());
                    }
                    if (resultSet.getObject(3) != null) {
                        outboundSurveyJsonResponseObject[arrayIndex].setCustomerNumber(resultSet.getString(3).trim());
                    }
                    if (resultSet.getObject(4) != null) {
                        outboundSurveyJsonResponseObject[arrayIndex].setQ1(resultSet.getString(4).trim());
                    }
                    if (resultSet.getObject(5) != null) {
                        outboundSurveyJsonResponseObject[arrayIndex].setQ2(resultSet.getString(5).trim());
                    }
                    if (resultSet.getObject(6) != null) {
                        outboundSurveyJsonResponseObject[arrayIndex].setQ3(resultSet.getString(6).trim());
                    }
                    if (resultSet.getObject(7) != null) {
                        outboundSurveyJsonResponseObject[arrayIndex].setQ4(resultSet.getString(7).trim());
                    }
                    if (resultSet.getObject(8) != null) {
                        outboundSurveyJsonResponseObject[arrayIndex].setQ5(resultSet.getString(8).trim());
                    }
                    arrayIndex++;
                }
                connection.close();
                return objectMapper.writeValueAsString(outboundSurveyJsonResponseObject);
            } catch (Exception e) {
                e.printStackTrace();
                return "[]";
            }
        }
        return "[]";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/createLogin")
    public String createLogin(@RequestBody String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateLogin jsonRequestObject = new CreateLogin();
        try {
            jsonRequestObject = objectMapper.readValue(payload, CreateLogin.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }

        if (jsonRequestObject.getUsername().isEmpty() || jsonRequestObject.getPassword().isEmpty()) {
            return "";
        } else if (jsonRequestObject.getUsername().length() > 25 || jsonRequestObject.getPassword().length() > 50) {
            return "";
        }
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        String connectionUrl = "jdbc:sqlserver://10.10.60.61:1433;databaseName=CustomDB;user=sa;password=Avaya123$";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String checkUsername = "select * from dbo.reportingLogins where username='" + jsonRequestObject.getUsername() + "'";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(checkUsername);
            resultSet.last();
            int rowsCount = resultSet.getRow();
            if (rowsCount > 0) {
                return "";
            } else {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(salt);
                byte[] hashedPassword = md.digest(jsonRequestObject.getPassword().getBytes(StandardCharsets.UTF_8));
                String SQL = "INSERT INTO dbo.reportingLogins (username , password, salt) VALUES  (?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
                preparedStatement.setString(1, jsonRequestObject.getUsername());
                preparedStatement.setBytes(2, hashedPassword);
                preparedStatement.setBytes(3, salt);
                int rs = preparedStatement.executeUpdate();
                System.out.println(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new String(salt));
        return "";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public String login(@RequestBody String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateLogin jsonRequestObject = new CreateLogin();
        try {
            jsonRequestObject = objectMapper.readValue(payload, CreateLogin.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }

        if (jsonRequestObject.getUsername().isEmpty() || jsonRequestObject.getPassword().isEmpty()) {
            return "";
        } else if (jsonRequestObject.getUsername().length() > 25 || jsonRequestObject.getPassword().length() > 50) {
            return "";
        }

        String connectionUrl = "jdbc:sqlserver://10.10.60.61:1433;databaseName=CustomDB;user=sa;password=Avaya123$";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String checkUsername = "select username, password, salt from dbo.reportingLogins where username='" + jsonRequestObject.getUsername() + "'";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(checkUsername);
            resultSet.last();
            int rowsCount = resultSet.getRow();

            if (rowsCount > 0) {
                resultSet.first();
                byte[] salt = resultSet.getBytes(3);
                byte[] password = resultSet.getBytes(2);
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(salt);
                byte[] hashedPassword = md.digest(jsonRequestObject.getPassword().getBytes(StandardCharsets.UTF_8));
                if (Arrays.equals(hashedPassword, password)) {
                    return "login approved";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return "";
    }
}
