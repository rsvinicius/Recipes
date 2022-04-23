# Recipes API

An urge to cook something special is too hard to resist sometimes. But what if you lost the recipe? Or your beloved grandma is too busy to answer a call and remind you of your favorite cake recipe? This multi-user web service with Spring Boot allows storing, retrieving, updating, and deleting recipes.

## Requirements

- Java 11+
- IntelliJ IDEA / Netbeans / Eclipse

## Usage

Start application in IDE or via command line:

```
./gradlew bootRun
```

**Default port**: 8881

## Endpoints

### POST: /api/v1/user/register
**Authentication:** not required

**Authorization:** not required

**Description:** receives a JSON object with two fields: email (string), and password (string). If a user with a specified email does not exist, the program saves (registers) the user in a database.

**Request example**:
```
{
   "email": "Cook_Programmer@somewhere.com",
   "password": "RecipeInBinary"
}
```

### POST: /api/v1/recipe/new
**Authentication:** required

**Authorization:** not required

**Description:** receives a recipe as a JSON object and returns a JSON object with one id field. This is a uniquely generated number by which we can identify and retrieve a recipe later.

**Request example**:
```
{
   "name": "Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

**Response example**:
```
{
   "id": 1
}
```

### GET: /api/v1/recipe/{id}
**Authentication:** required

**Authorization:** not required

**Description:** returns a recipe with a specified id as a JSON object. 

**Response example**:
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "date": "2020-01-02T12:11:25.034734",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

### DELETE: /api/v1/recipe/{id}
**Authentication:** required

**Authorization:** required (only recipe owner)

**Description:** deletes a recipe with a specified id. 

### PUT: /api/v1/recipe/{id}
**Authentication:** required

**Authorization:** required (only recipe owner)

**Description:** receives a recipe as a JSON object and updates a recipe with a specified id.

**Request example**:
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

### GET: /api/v1/recipe/search
**Authentication:** required

**Authorization:** not required

**Description:** takes one of the two mutually **exclusive** query parameters:
- **category** – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first);
- **name** – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).

**Response example for the GET /api/v1/recipe/search/?category=dessert request:**
```
[
   {
      "name": "Vegan Chocolate Ice Cream",
      "category": "dessert",
      "date": "2021-04-06T14:10:54.009345",
      ....
   },
   {
      "name": "vegan avocado ice cream",
      "category": "DESSERT",
      "date": "2020-01-06T13:10:53.011342",
      ....
   },
]
```

**Response example for the GET /api/v1/recipe/search/?name=tea request:**
```
[
   {
      "name": "Fresh Mint Tea",
      "category": "beverage",
      "date": "2021-09-06T14:11:51.006787",
      ....
   },
   {
      "name": "warming ginger tea",
      "category": "beverage",
      "date": "2020-08-06T14:11:42.456321",
      ....
   },
   {
      "name": "Iced Tea Without Sugar",
      "category": "beverage",
      "date": "2019-07-06T17:12:32.546987",
      ....
   },
]
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.  Please make sure to update tests as appropriate.

## License

Usage is provided under the [MIT License](https://mit-license.org/). See LICENSE for full details.
