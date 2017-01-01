pushd ..
echo "This could take more than a few minutes..."
echo "The following is the current branch that we'll be pushing."
git branch
sbt compile && sbt assembly
aws s3 cp --region us-east-1 target/scala-2.11/DratiniDex-assembly-1.0.jar s3://com.clydemachine.dratinidex/DratiniDex.jar --debug
clear
aws lambda update-function-code --region us-east-1 --function-name "DratiniDex" --s3-bucket "com.clydemachine.dratinidex" --s3-key "DratiniDex.jar" --debug
popd
echo "----"
echo "Done! Test the skill now.\nRemember that updates to IntentSchema, POKENAMES list, or the SampleUtterances MUST BE DONE MANUALLY."
read -n1 -r -p "Press any key to continue..." key