# Java Dynamite Runner
This is a console app to allow users to test their dynamite bots offline against a few simple opponents.

# Set up
* The program should "just run" using your preferred method to run a console app
* Verify that the program runs using the `main` method in [Application.java](./src/main/java/Application.java)
  * It should throw a NotImplementedException at this stage
* Copy and paste your bot logic into [YourBot.java](./src/main/java/bots/YourBot.java)
* Run the program

# Testing more bots
* To test multiple bots against each other:
  * Add any new bots to the bots folder
  * Add a new call to `RunGameBetween` with the relevant bots in `Application::Run`
