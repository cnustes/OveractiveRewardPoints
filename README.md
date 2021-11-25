# OveractiveRewardPoints
Exercises to be evaluated by the company Overactive, SpringBoot  + H2 + ApiRest

## Description:
A retailer offers a rewards program to its customers, awarding points based on each recordedpurchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

## Goals achieved
1. Code your solution in Java and Spring
2. Make up a data set to best demonstrate your solution
3. Use OOP concepts as much as possible when designing classes.
4. Swagger/OpenAPI (Optional) - helps communicate the contract in a better way
5. Document all response codes expected. The REST API should be sending the appropriate code and not 200 always.
6. Functional Programming constructs of Java 8 as appropriate.
7. Generic exception handler for un-anticipated exceptions.

8. Follow standard best practices for structuring the code.
9. Prepare and provide Test Data along with the code.
10. Implement RestAPI’s for all CRUD operations – in this case – creating/updating transactions, calculating and providing reward information for a User.
11. Use of appropriate logging levels, framework
12. Reward calculation logic should be accurate

## How to Run
### From Eclipse (STS) Using GIT:
1. Open File->Import... and select Git->Git Projects
2. In the next screen, select "Clone URI".
3. We provide the url of the repository in the URI field, the rest will be filled in automatically.
4. After clicking Next> , Eclipse will connect to the repository and show the branches, you must select "dev".
5. Indicate the location where you want to download the project.
6. Clicking Next> will download the repository to the chosen location. We can import the project into Eclipse using option 3 "Import as general project".

### From CMD
1. Download the project in .zip from the "dev" branch.
2. Extract in directory.
3. Use the mvnw.cmd command to execute maven commands and compile, install dependencies.
4. Execute mvnw install which will install all the dependencies at the same time as it compiles the application.
5. Use java -jar targetwebapp2-0.0.1-SNAPSHOT.jar to start the application.
