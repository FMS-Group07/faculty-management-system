@echo off
echo Compiling Project...
if not exist "out\production\faculty-management-system" mkdir "out\production\faculty-management-system"

dir /s /b src\*.java > sources.txt
javac -cp "mysql-connector-j-9.5.0/mysql-connector-j-9.5.0.jar" -d "out/production/faculty-management-system" @sources.txt
del sources.txt

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Running Application...
java -cp "mysql-connector-j-9.5.0/mysql-connector-j-9.5.0.jar;out/production/faculty-management-system" com.faculty.main.Main
pause
