rem **Create blankMod**
del "..\Release\BronzeAgeToImperator\blank_mod" /Q /S /F
rmdir "..\Release\BronzeAgeToImperator\blank_mod" /Q /S
xcopy "DataFiles\blank_mod" "..\Release\BronzeAgeToImperator\blank_mod" /Y /E /I

copy "DataFiles\Readme.txt" "..\Release"
copy "DataFiles\license.txt" "..\Release"

git rev-parse HEAD > ..\Release\commit_id.txt