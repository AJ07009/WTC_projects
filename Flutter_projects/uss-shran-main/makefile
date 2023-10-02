# test comment

script-executable:
	@chmod u+x build_script.sh

clean:
	@mvn clean
	@echo "Target has been Cleaned"

#DOES NOT WORK DO NOT USE!!!!!!
#verify:
#	@mvn verify
edit-version:
	@./build_script.sh edit_version

hard-version:
#	@mvn --batch-mode release:update-versions -DdevelopmentVersion=$(VERSION)-SNAPSHOT
	@mvn --batch-mode release:update-versions -DdevelopmentVersion=1.1.1-SNAPSHOT

git-tag:
	@./build_script.sh git_version

compile:
	@mvn compile || echo "Build Failed"

package-notest: script-executable
	@mvn -DskipTests package

package-test: package-notest
	@./build_script.sh run_server_test

docker-test: script-executable
	@./build_script.sh docker_test_server

docker-run: docker-build
	@sudo docker run -i -p 5001:5000 gitlab.wethinkco.de:5050/agioio/uss-shran:latest

#docker-tag:
#	@sudo docker tag echo-server:0.0.0-snapshot gitlab.wethinkco.de:5050/agioio/uss-shran

docker-push: docker-build
	@sudo docker push gitlab.wethinkco.de:5050/agioio/uss-shran

#--no-cache <--- to not use cache FRESH BUILD
docker-build: docker-login
	@sudo docker build -t gitlab.wethinkco.de:5050/agioio/uss-shran .

docker-pull: docker-login
	@sudo docker pull gitlab.wethinkco.de:5050/agioio/uss-shran:latest

docker-login:
	@sudo docker login gitlab.wethinkco.de:5050 -u="jkokot@student.wethinkcode.co.za" -p="J@ker2401"

directory = ~/apk_ouput

setup_pub:
	@ cd Client
	@ flutter pub get
	@ flutter pub outdated
	@ flutter pub upgrade

setup_flutter:
	@ cd Client
	@ flutter doctor

upgrade_flutter:
	@ cd Client
	@ flutter upgrade

build_apk: | $(directory)
	@ cd Client
	@ flutter build apk

move_apk: build_apk | $(directory)
	@ cd Client
	@ cp build/app/outputs/apk/release/app-release.apk apk_ouput/quote_apk.apk

$(directory):
	@ echo "Folder $(directory) does not exist"
	@ mkdir -p $@
