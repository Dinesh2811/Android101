const express = require('express');
const { graphqlHTTP } = require('express-graphql');
const { buildSchema } = require('graphql');

// Sample data for persons
const persons = [
  {
    name: 'John',
    age: 30,
    address: {
      street: '123 Main St',
      city: 'New York',
      country: 'USA'
    }
  },
  {
    name: 'Smith',
    age: 30,
    address: {
      street: '456 Elm St',
      city: 'Los Angeles',
      country: 'USA'
    }
  },
  {
    name: 'Alice',
    age: 35,
    address: {
      street: '789 Oak St',
      city: 'Chicago',
      country: 'USA'
    }
  }
];

// Define your GraphQL schema
const schema = buildSchema(`
  type Query {
    hello: String
    numbers: [Int]
    persons: [Person]
    person(name: String): Person
    searchPersons(name: String, age: Int): [Person]
  }

  type Person {
    name: String
    age: Int
    address: Address
  }

  type Address {
    street: String
    city: String
    country: String
  }
`);

// Define your resolvers
const root = {
  hello: () => 'Hello, world!',
  numbers: () => [1, 2, 3, 4, 5],
  persons: () => persons,
  person: ({ name }) => persons.find(person => person.name === name),
  searchPersons: ({ name, age }) => {
    // Use destructuring and a more concise filter
    return persons.filter(person => (!name || person.name === name) && (!age || person.age === age));
  }
};

// Create an Express server
const app = express();

// Define the GraphQL endpoint
app.use('/graphql', graphqlHTTP({
  schema: schema,
  rootValue: root,
  graphiql: true
}));

// Log the server URL before starting
const port = 3000;
app.listen(port, () => {
  console.log(`Server started on http://localhost:${port}/graphql`);
});
