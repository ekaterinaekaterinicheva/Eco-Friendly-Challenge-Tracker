# Eco-Friendly Challenge Tracker App
This is an Android app that can help users create a routine of simple, everyday eco-friendly habits by logging progress and track achievements over time.

## App Features
- Enables users to set a goal by selecting a sustainability challenge from a predefined list and defining a duration to complete this goal.
- Enables users to log daily progress (success or missed attempts at completing a challenge once per day).
- Provides progress visualization using a dynamic progress bar.
- Provides an overview of all logged entries with dates and outcomes (success or missed attempts).
- Provides goal management:
  - Allowing users to log only one active goal at a time.
  - Allowing users to delete the current goal.
- Provides local data storage via Room.
- Provides a clean MVVM architecture.

## MVVM App Architecture
![Component_Interaction_Diagram](https://github.com/user-attachments/assets/25355660-33a5-4c8d-8b56-b77c8a602d96)

## App Screenshots
### Home Screen
<img width="600" height="600" alt="Home_Screen_Two_States" src="https://github.com/user-attachments/assets/47f22a41-57ec-43d4-9d3e-36569cfd9c58" />

### Set Goal Screen
<img width="600" height="600" alt="Set_Goal_Screen_Two_States" src="https://github.com/user-attachments/assets/d4935c22-e6bf-4dcc-92fb-90038d093365" />

### Log Progress Screen
<img width="600" height="600" alt="Log_Progress_Screen_Two_States" src="https://github.com/user-attachments/assets/74ec5c68-5060-4291-8c63-75daa4cac322" />

### History Screen
<img width="600" height="600" alt="History_Screen_Two_States" src="https://github.com/user-attachments/assets/f0688616-e1d7-4e40-bdb5-34fd67ce51cd" />

## Tech Stack
- User Interface: Built with Jetpack Compose, styled using Material Design 3 components.
- App Logic: Follows the MVVM Pattern to ensure a separation of concerns and utilizes LiveData.
- Data Management: Room DB provides an abstraction layer over SQLite for data storage.
- Testing: Unit testing implemented via JUnit to ensure logic reliability.
- Design: Prototypes mockups created in Figma.

## Initialize & Install
1. Open Android Studio.
2. Go to File -> New -> Project from Version Control...
3. Paste the repository link.
4. Allow Gradle to sync.
5. Turn on an Android emulator or connect a real device.
6. Click Run.
