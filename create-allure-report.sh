#!/bin/bash

echo "-----RUN-TESTS-&-SEND-RESULTS-&-GENERATE-REPORT-----"
mvn clean verify && mvn compile exec:java -Dexec.mainClass="util.SendReportFiles" && mvn compile exec:java -Dexec.mainClass="util.GenerateReport"
