pushd ..
git branch
sbt compile && sbt assembly
aws lambda update-function-code --region us-east-1 --function-name "AlexaBulba" --zip-file fileb://target/scala-2.11/Alexa-Bulbapedia-Scala-assembly-1.0.jar
clear
echo "----"
echo "Done! Test the skill now."