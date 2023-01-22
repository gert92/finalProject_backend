# Project Title

Travel Agency web application



Main functionalities:

1. making trip reservation by checking the trip availability from callender
2. view all available hotels
3. view cities and countries the agency offers trips to
4. view starting prices for different packages
5. view available trips in each country
6. possibility to add new Users and admins
7. different access levels bases on type of user
8. search bar with capability to search city,country and hotel

## Authors

- [@Soroush Malekpour](https://github.com/soroush16)
-  [@Gert Moisin](https://github.com/gert92)
-   [@Egerd Valdmaa](https://github.com/Egerd1)





## API Reference

#### Post city

```http
  POST /api/cities
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `object` | **Required**. Request Body |

#### get city

```http
  GET /api/cities/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `object` | **Required**. Id of city to fetch |


#### get cities

```http
  GET /api/cities
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|       | `list of object` ||


#### update a city

```http
  PUT /api/cities/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|   `id`        | `object` | **Required**. Id of city to to be updated |




#### delete a city

```http
  DELETE /api/cities/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|    `id`       | | **Required**. Id of city to be deleted |


#### delete all cities

```http
  DELETE /api/cities
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|           |  |  |


#### Post country

```http
  POST /api/countries
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `object` | **Required**. Request Body |


#### get country

```http
  GET /api/countries/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `object` | **Required**. Id of country to fetch |


#### get countries

```http
  GET /api/countries
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|       | `list of object` ||


#### update a country

```http
  PUT /api/countries/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|   `id`        | ` object` | **Required**. Id of country to to be updated|


#### delete a country

```http
  DELETE /api/countries/{id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|    `id`       | | **Required**. Id of the country to be deleted |


#### delete all countries

```http
  DELETE /api/countries
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
|           |  |  |


The rest of endpoints follow the same logic as previous ones










## Demo

Live server

https://final-project-frontend-ten.vercel.app/
