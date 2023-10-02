directory = ~/apk_ouput

setup_pub:
    @ flutter pub get
    @ flutter pub outdated
    @ flutter pub upgrade

setup_flutter:
    @ flutter doctor

upgrade_flutter:
    @ flutter upgrade

build_apk: | $(directory)
    @ flutter build apk

move_apk: build_apk | $(directory)
    @ cp build/app/outputs/apk/release/app-release.apk apk_ouput/quote_apk.apk

$(directory):
    @ echo "Folder $(directory) does not exist"
    @ mkdir -p $@