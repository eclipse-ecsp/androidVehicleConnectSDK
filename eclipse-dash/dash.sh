#!/bin/bash

echo 'Start checking All Dependencies'

chmod +x 'gradlew'

echo 'Checking dependencies for SDK module'
'./gradlew' 'androidVehicleConnectSDK:dependencies' | 'grep' '-Poh' "(?<=\-\-\- ).*" | 'grep' '-Pv' "\([c\*]\)" | 'grep' '-Pv' "\([n\*]\)" | 'perl' '-pe' 's/([\w\.\-]+):([\w\.\-]+):(?:[\w\.\-]+ -> )?([\w\.\-]+).*$/$1:$2:$3/gmi;t' | 'sort' -u > 'SdkDependenciesItem'

echo 'Checking dependencies for SDK implemented test app Module'
'./gradlew' 'app:dependencies' | 'grep' '-Poh' "(?<=\-\-\- ).*" | 'grep' '-Pv' "\([c\*]\)" | 'grep' '-Pv' "\([n\*]\)" | 'perl' '-pe' 's/([\w\.\-]+):([\w\.\-]+):(?:[\w\.\-]+ -> )?([\w\.\-]+).*$/$1:$2:$3/gmi;t' | 'sort' -u | 'grep' '-v' 'project :androidVehicleConnectSDK' > 'SdkTestDependenciesItem'


echo 'Tool checking for SDK dependencies'

chmod +x $PWD/eclipse-dash/dash.jar $PWD/SdkDependenciesItem
java -jar $PWD/eclipse-dash/dash.jar $PWD/SdkDependenciesItem -summary $PWD/SDK_NOTICE.md

echo 'Tool checking for SDK test case application dependencies'

chmod +x $PWD/eclipse-dash/dash.jar $PWD/SdkTestDependenciesItem
java -jar $PWD/eclipse-dash/dash.jar $PWD/SdkTestDependenciesItem -summary $PWD/SDK_TEST_NOTICE.md
