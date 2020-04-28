# REST API Solution

## Running the tests

To run the tests you will need Java installed (should work with either Java 8 or 11).

You can run the tests by executing:

```
./gradlew clean test
```

## Assumptions

For simplicity, I assumed that the data in the test environment is persisted. I used a pre-existing politician id
to test getting the information for a particular politician.

The disadvantage of that is that the test can fail if the data is edited or deleted. The advantage is that 
we can test the GET call to that endpoint independently.

In a scenario where our test data might be altered, the test can be rewritten to create a new test politician 
every time the test class is run, and get the details we used in creation. This has the disadvantage that if the 
creation functionality is down, the test trying to read data will also fail.

I assumed the id `123456789123456789123456` does not exist in the system. 

I assumed the validation of length for String fields out of scope and covered in unit tests by the team.

I assumed non-functional requirements suck as performance and security of the API are out of scope.

## Issues found

Testing revealed a number of issues:

- Getting an existing politician by id always returns their year of birth as 0.
- The fields risk and country are not mandatory for creating a politician. 
- Trying to get a politician with an id of the correct length that does not exist in the system returns an error 
that says 404 in the response body, but in fact the status code is 200.
- The call to retrieve the last 5 politicians returns the last 10 instead.
- You can create a politician with a risk value outside of the 1-5 range.
- You can create a politician with a negative year of birth.
- You can create a politician with any of the mandatory fields set to null as well as string fields set to `""`. 
It's unclear from the requirements which if any of the fields should accept null or empty values.

For the last point I did not write any tests, in the interest of speed. 

In a real team scenario I would first clarify the requirements surrounding null and empty values as well as what 
validation we want to do on these inputs.
Then I would sit with the developers and advocate for adding unit-level tests to cover these corner cases.
