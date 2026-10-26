
## Project Evolution

This project started as a console-based OOP application (Java) built for
my Object-Oriented Programming course. It modeled a government impound
system using inheritance (abstract `ImpoundRecord` extended by
`CourtImpound` and `ViolationImpound`), polymorphism, and composition,
with all data held in memory.

I later extended it into a full-stack web application:

- **Persistence:** replaced in-memory arrays with an SQLite database
  designed from an ER model and normalized to 3NF.
- **Architecture:** split the single `GovernmentImpoundSystem` class into
  layers (`ApiController`, `ImpoundService`, `Repository`,
  `DatabaseManager`) for separation of concerns.
- **Web layer:** built a REST API with Spring Boot and a browser-based UI.
- **New features:** role-based access (Admin, Police Officer, Employee,
  Vehicle Owner), vehicle image upload, payment tracking, and an owner
  dashboard.

The original OOP design is preserved in the class diagram below, and the
OOP concepts carried over into the final system.


 Project Video Link


https://onedrive.live.com/?qt=allmyphotos&photosData=%2Fshare%2F27703F14AAB51AB8%21s5fe993ac3d7b46a19a2963038250b66d%3Fithint%3Dvideo%26e%3DZbDUoi%26migratedtospo%3Dtrue&cid=27703F14AAB51AB8&id=27703F14AAB51AB8%21s5fe993ac3d7b46a19a2963038250b66d&redeem=aHR0cHM6Ly8xZHJ2Lm1zL3YvYy8yNzcwM2YxNGFhYjUxYWI4L0lRQ3NrLWxmZXoyaFJwb3BZd09DVUxadEFWX3kzS0xKd3hXWnl0aFlYcXdFa0lnP2U9WmJEVW9p&v=photos
