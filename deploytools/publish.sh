pushd ..
echo "This could take more than a few minutes..."
echo "The following is the current branch that we'll be pushing."
git branch
sbt compile && sbt assembly
aws s3 cp --region us-east-1 target/scala-2.11/Alexa-Bulbapedia-Scala-assembly-1.0.jar s3://com.clydemachine.alexabulba/AlexaBulba.jar --debug
clear
aws lambda update-function-code --region us-east-1 --function-name "AlexaBulba" --s3-bucket "com.clydemachine.alexabulba" --s3-key "AlexaBulba.jar" --debug
popd
echo "----"
echo "Done! Test the skill now."
read -n1 -r -p "Press any key to continue..." key