#!/bin/bash

echo 'Start checking All Dependencies'

chmod +x 'gradlew'

echo 'Checking dependencies for SDK module'
'./gradlew' 'androidVehicleConnectSDK:dependencies' | 'grep' '-Poh' "(?<=\-\-\- ).*" | 'grep' '-Pv' "\([c\*]\)" | 'grep' '-Pv' "\([n\*]\)" | 'perl' '-pe' 's/([\w\.\-]+):([\w\.\-]+):(?:[\w\.\-]+ -> )?([\w\.\-]+).*$/$1:$2:$3/gmi;t' | 'sort' -u > 'SdkDependenciesItem'

echo 'Tool checking for SDK dependencies'
echo "File Path, $PWD "

chmod +x $PWD/eclipse-dash/dash.jar $PWD/SdkDependenciesItem
java -jar $PWD/eclipse-dash/dash.jar $PWD/SdkDependenciesItem -summary $PWD/SDK_NOTICE.md
