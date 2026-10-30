# MultiBackCrud

This is a monorepo build primarily for learning purposes. It's goal is to portray the same API behavior in several programming languages to compare and learn from all in a safe, predictable manner.

## Repository instructions

This is the canonical repository-wide instruction file.

Harness-specific entry points should bootstrap from this file. Subdirectory-level `AGENTS.md` files may add technology-specific guidance for the files below them.

## Tech Stack

Languages in the monorepo are:

- Java
- Python
- Kotlin
- Rust
- Typescript
- Golang

Each might use specific frameworks and tools, but the core functionality and architecture must be the same, adapting to each language caveats. 

Languages may or not be available depending on the current branch.

## Shared technology

All CRUDs will connect to the same Postgresql database for persistent data.

Caching will be done through REDIS. Data stored here must be serialized and deserialized as needed.

For the frontend, no API should "trust" requests will come from the same source. A parallel, similar project, MultiFrontCrud is in development.