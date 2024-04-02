*** Settings ***
Library    SeleniumLibrary
Library    RequestsLibrary
Library    Collections
Library    OperatingSystem

*** Variables ***
${BASE_URL}                http://localhost:8080/student-test-app/v1/
${json_path}    C:/Users/Lea/Desktop/Sub/src/test/


*** Test Cases ***
Valid_Subscription_Creation_Test
    [Documentation]    Test creating a valid subscription
    Create Session    subscription    ${BASE_URL}
    ${headers}    Create Dictionary  Content-Type=application/json; charset=utf-8
    ${json}  Get Binary File  ${json_path}data.json
    ${response}   Post Request   subscription     http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    Should Be Equal As Strings    ${response.status_code}    201
    Delete All Sessions

Invalid_Subscription_Creation_Test_parameter_types
    [Documentation]    Test creating an invalid subscription (Invalid parameter types)
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}  Get Binary File  ${json_path}invalid_json.json
    ${response}   Post Request   subscription     http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    Should Be Equal As Strings    ${response.status_code}    502
    Delete All Sessions

Invalid_Subscription_Creation_Test_mandatory_parameters
    [Documentation]    Test creating an invalid subscription (missing mandatory parameters)
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}  Get Binary File  ${json_path}invalid_json2.json
    ${response}   Post Request   subscription     http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    Should Be Equal As Strings    ${response.status_code}    502
    Delete All Sessions

Subscription_Deletion_Test
    [Documentation]    Test deleting a subscription
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}  Get Binary File  ${json_path}data.json
    ${response}   Post Request   subscription     http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    ${response_data}=    Set Variable    ${response.json()}
    log to console   \nOriginal JSON:\n${response}
    log to console   \nJSON::\n${response_data}
   ${subscription_id}=    Set Variable    ${response_data['subscriptionId']}
    ${response}=    DELETE On Session    subscription    /subscriptions/${subscription_id}
    Should Be Equal As Strings    ${response.status_code}    204
    Delete All Sessions

Negative test case:Subscription_Deletion_Test_non_existis
    [Documentation]    Test deleting a non-existing subscription
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}    Get Binary File    ${json_path}data.json
    ${response}    Post Request    subscription    http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    ${response}    DELETE On Session    subscription    /subscriptions/78  expected_status=404
    Should Be Equal As Strings    ${response.status_code}    404
    Delete All Sessions

Retrieve_Full_Subscription
    [Documentation]    Retrieve full subscription details
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}    Get Binary File    ${json_path}data.json
    ${response}    Post Request    subscription    http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    ${response_data}=    Set Variable    ${response.json()}
    ${subscription_id}=    Set Variable    ${response_data['subscriptionId']}
    ${response}    Get Request    subscription    /subscriptions/${subscription_id}
    Should Be Equal As Strings    ${response.status_code}    200
     Delete All Sessions

Retrieve_Full_Subscription_Query_Parameter_EventFilters
    [Documentation]    Retrieve full subscription details with eventFilters query parameter
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}    Get Binary File    ${json_path}data.json
    ${response}    Post Request    subscription    http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    ${response_data}=    Set Variable    ${response.json()}
    ${subscription_id}=    Set Variable    ${response_data['subscriptionId']}
    ${events}=    Set Variable    ${response_data['subscribedEvents']}
    ${response}    Get Request    subscription    http://localhost:8080/student-test-app/v1/subscriptions/${subscription_id}?subscibedEvents=${events}
    Should Be Equal As Strings    ${response.status_code}    200
     Delete All Sessions

Retrieve_Full_Subscription_Query_Parameter_ Snssai
    [Documentation]    Retrieve full subscription details with Snssai query parameter
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}    Get Binary File    ${json_path}data.json
    ${response}    Post Request    subscription    http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    ${response_data}=    Set Variable    ${response.json()}
    ${subscription_id}=    Set Variable    ${response_data['subscriptionId']}
    ${snssai}=    Set Variable    ${response_data['snssai']['cn']}
    ${response}    Get Request    subscription    http://localhost:8080/student-test-app/v1/subscriptions/${subscription_id}?snssai=${snssai}
    Should Be Equal As Strings    ${response.status_code}    200
     Delete All Sessions

Negative Test Case:Retrive_Non-existing_Subscription
    [Documentation]    Verify response status code when attempting to delete a non-existing subscription
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}    Get Binary File    ${json_path}data.json
    ${response}    Post Request    subscription    http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    ${response}    Get Request    subscription    /subscriptions/78  
    Should Be Equal As Strings    ${response.status_code}    404
    Delete All Sessions

Negative Test Case:Retrive_Non-existing_Snssai
    [Documentation]    This test verifies the retrieval of a non-existing subscription.
    Create Session    subscription    ${BASE_URL}
    ${headers}=    Create Dictionary    Content-Type=application/json
    ${json}    Get Binary File    ${json_path}data.json
    ${response}    Post Request    subscription    http://localhost:8080/student-test-app/v1/subscriptions    data=${json}    headers=${headers}
    ${response}    Get Request    subscription    /subscriptions/78  
    Should Be Equal As Strings    ${response.status_code}    404
    Delete All Sessions
