#!/bin/bash

# shellcheck disable=SC1009
# shellcheck disable=SC2046
# shellcheck disable=SC2181
# shellcheck disable=SC1073
# shellcheck disable=SC1124
# shellcheck disable=SC2221
# shellcheck disable=SC2222

set -e

our_server_test_size2_obs1_1(){
  VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
  v2=${VERSION::-9}
  mvn package -Dmaven.test.skip
  java -jar Server/target/robotWorld-"$v2"-SNAPSHOT-socket-jar-with-dependencies.jar -p 5000 -s 2 -o 1,1 &
  sleep 3s
  mvn test -q -DfailIfNoTests=false -Dtest=LaunchRobotTests#validLaunchShouldSucced
  mvn test -q -DfailIfNoTests=false -Dtest=LaunchWithObstaclesTest
  mvn test -q -DfailIfNoTests=false -Dtest=LauchWithObstaclesTest#worldWithObstacleIsFull
  echo "Killing server"
  kill $(lsof -t -i:5000)
  sleep 3s
  echo "port 5000 has been freed"
  sleep 3s
  java -jar Server/target/robotWorld-"$v2"-SNAPSHOT-socket-jar-with-dependencies.jar -p 5000 -s 1 &
  sleep 3s
  mvn test -q -DfailIfNoTests=false -Dtest=LaunchSuccessTest#successLaunch
  mvn test -q -DfailIfNoTests=false -Dtest=ForwardAtEdgeTest#validForwardCommandAtEdge
  echo "Killing server"
  kill $(lsof -t -i:5000)
  sleep 3s
  echo "port 5000 has been freed"
  }

our_server_test_size2(){
  VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
  v2=${VERSION::-9}
  mvn package -Dmaven.test.skip
  java -jar Server/target/robotWorld-"$v2"-SNAPSHOT-socket-jar-with-dependencies.jar -p 5000 -s 2 &
  sleep 3s
  mvn test -q -DfailIfNoTests=false -Dtest=LaunchRobotTests#worldWithoutObsIsFull
  mvn test -q -DfailIfNoTests=false -Dtest=LaunchRobotTests#invalidLaunchShouldFail
  mvn test -q -DfailIfNoTests=false -Dtest=LaunchRobotTests#tooManyNames
  mvn test -q -DfailIfNoTests=false -Dtest=LaunchRobotTests#anotherOne
  mvn test -q -DfailIfNoTests=false -Dtest=LauchWithObstaclesTest#robotsShouldNotSpawnOn1x1
  echo "killing server"
  kill $(lsof -t -i:5000)
  sleep 3s
  echo "port 5000 has been freed"
}

our_server_look_state(){
  VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
  v2=${VERSION::-9}
  mvn package -Dmaven.test.skip
  java -jar Server/target/robotWorld-"$v2"-SNAPSHOT-socket-jar-with-dependencies.jar -p 5000 -s 1 &
  mvn test -q -DfailIfNoTests=false -Dtest=StateRobotTest#validStateShouldSucceed
  mvn test -q -DfailIfNoTests=false -Dtest=StateRobotTest#invalidStateShouldFail
  sleep 3s
  echo "killing server"
  kill $(lsof -t -i:5000)
  sleep 3s
  echo "port 5000 has been freed"
  java -jar Server/target/robotWorld-"$v2"-SNAPSHOT-socket-jar-with-dependencies.jar -p 5000 -s 2 &
  sleep 3s
  mvn test -q -DfailIfNoTests=false -Dtest=LookEmptyTest
  echo "killing server"
  kill $(lsof -t -i:5000)
  sleep 3s
  echo "port 5000 has been freed"
  java -jar Server/target/robotWorld-"$v2"-SNAPSHOT-socket-jar-with-dependencies.jar -p 5000 -s 2 -o 1,1 &
  sleep 3s
  mvn test -q -DfailIfNoTests=false -Dtest=LookRobotTest
  mvn test -q -DfailIfNoTests=false -Dtest=LookObsTest
  sleep 3s
  echo "killing server"
  kill $(lsof -t -i:5000)
  sleep 3s
  echo "port 5000 has been freed"
  java -jar Server/target/robotWorld-"$v2"-SNAPSHOT-socket-jar-with-dependencies.jar -p 5000 -s 2 -o 1,1 &
  sleep 3s
  mvn test -q -DfailIfNoTests=false -Dtest=LookBothCheckTest
  sleep 3s
  echo "killing server"
  kill $(lsof -t -i:5000)
  sleep 3s
  echo "port 5000 has been freed"
}

api_server_test(){
  echo "Doing API Testing"
  mvn test -q -DfailIfNoTests=false -Dtest=ApiServerTests
}

our_server_run_all(){
  our_server_look_state
  if [ $? -eq 0 ]
  then
      echo "it worked"
  else
      echo "it failed"
  fi
  wait
  our_server_test_size2_obs1_1
  if [ $? -eq 0 ]
  then
      echo "it worked"
  else
      echo "it failed"
  fi
  wait
  our_server_test_size2
  if [ $? -eq 0 ]
  then
      echo "it worked"
  else
      echo "it failed"
  fi
  wait
  api_server_test
    if [ $? -eq 0 ]
    then
        echo "it worked"
    else
        echo "it failed"
    fi
    wait
}

run_server_test(){
  our_server_run_all
  if [ $? -eq 0 ]
  then
      echo "it worked"
  else
      echo "it failed"
  fi
}

git_versioning(){
  mvn package -Dmaven.test.skip
    if [[ "$?" -ne 0 ]] ; then
      echo "A test has failed, so it could not be packaged."
    else
      # removing folder
      if [ -d "outputs" ]; then
        # Control will enter here if $DIRECTORY exists.
        rm -r 'outputs'
      fi
      # adding folder
      mkdir 'outputs'
      VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
      v2=${VERSION::-9}
      cp Server/target/Server-"$v2"-SNAPSHOT-jar-with-dependencies.jar outputs/robot-worlds-server-"$v2".jar
      git tag -a "$v2" -m "my version $v2"
      # shellcheck disable=SC2154
      git push origin "$v2"; exit $rc
      exit 0
      fi
}

edit_pom_version(){
  echo "What would you like to name this version: (format: x.x.x)"
  # shellcheck disable=SC2162
  # shellcheck disable=SC2034
  read version_var
  mvn --batch-mode release:update-versions -DdevelopmentVersion="$version_var"-SNAPSHOT
}

case $1 in
  "run_server_test") run_server_test;;
  "git_version") git_versioning;;
  "edit_version") edit_pom_version;;
  *) echo "Unsupported function" ;;
esac