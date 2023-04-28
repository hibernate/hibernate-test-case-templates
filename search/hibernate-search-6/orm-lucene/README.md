# Reproducer

## How to reproduce

1. Run `YourIT` from the IDE.
2. Look at the logs:
   ```
   Initializing...
   Indexing Manufacturer#1
   Indexing Article#2
   Indexing Article#3
   Initialized.
   Updating...
   Updated
   ```
3. Revert the last commit to get back to ORM 6.2: `git revert HEAD`.
4. Refresh your IDE (`Reload all Maven projects` or equivalent).
5. Run `YourIT` from the IDE.
6. Look at the logs:
   ```
   Initializing...
   Indexing Manufacturer#1
   Indexing Article#2
   Indexing Article#3
   Initialized.
   Updating...
   Indexing Manufacturer#1
   Indexing Article#2
   Indexing Article#3
   Updated
   ```

## How to debug

* Put a breakpoint in `org.hibernate.search.mapper.orm.event.impl.HibernateSearchEventListener.onPostUpdate`
* Use the following code in your IDE's "evaluate" feature to render `dirtyProperties`:
  ```
   List<String> dirtyPropertyNames = new ArrayList<>();
   for (int i : dirtyProperties) {
       dirtyPropertyNames.add( event.persister.getPropertyNames()[i] );
   }
   return dirtyPropertyNames.toString();
   ```
* See how with ORM 6.2, `dirtyProperties` always contains `textId`,
  even though `textId` didn't change.