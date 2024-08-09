# MultiBackCrud 🔀
Wanna learn more hands on. So a crud with multiple backends and a single front end I will.

I will build a very, VERY simple front end as a starting point, to later on work with a more proper front end. I'm thinking Svelte, of course, but HTMX and Angular are my second options. Or maybe I will just bulid a Multi**FRONT**Crud, for that matter.

## About the datasource
I came accros a fantastic repo for this: 

[Mr. Ben's (databasestar) collection of cool resources](https://github.com/bbrumm/databasestar/tree/main/sample_databases/sample_db_gravity)

The data layers of the projects in this repository are tailored to this source, so it is required to have it. I went with the Postgres flavor but use what you see fit. I will try to make the configuration of the datasources as configurable as possible in order to make migrating from one engine to another easy. I might even try to build a script to automatically re-configure the datasource configurations of every backend with some stock values (i.e. localhost/127.0.0.1 as the target host). As I'm writing this, I can think of a lot of cool little things to do, but let's not over complicate things at this point.

### 'That repo has a lot of tables. Do I need them all?'

No, you will only need the tables

* author
* publisher
* book_language
* book
* author_book

and it's corresponding data in their own 'populate' sql file in the repo. Note that the information for `book_language` is in the 'lookup' file, and along with it there is information for other tables not used, so take only the inserts for `book_language`. 


