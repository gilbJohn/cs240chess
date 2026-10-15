# Learning notes

## Teaching preferences
- Explain unfamiliar concepts without repeating basic Java syntax.
- Guide implementation in small steps and explain the reasons for the code.
- Do not assume understanding of a concept merely because code was copied.
- Keep these notes local to this project, outside Google services.

## Demonstrated or stated understanding
- Basic Java method declarations and public visibility.
- Maps hold server data in memory.
- An implementation connects to an interface with implements; @Override checks that relationship.
- Passing a storage reference into a service lets it use the same storage object.

## Topics to develop
- Javalin Context: how handlers access requests and construct responses.
- Registering routes and connecting storage, services, and handlers.
- Handling exceptions and producing JSON error responses.
- Writing meaningful service tests.

## Current goal
- Complete DELETE /db end to end, then verify removal of populated storage with a service test.
