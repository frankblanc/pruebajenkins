Feature: Login and Logout Action
Description: Login, make and appointment, and logout

Scenario: Login
	Given User is on homepage
	When User Login
	Then Message displayed Login succesfully

Scenario: Make an appointment
	Given User is on appointment page
	When User enter appointment data
	Then Message displayed appointment creation succesful

Scenario: Logout
	Given User is on appointment summary
	When User clicks logout button
	Then Message displayed Logout succesfully