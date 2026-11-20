# Employee Management — Module 5

Module 5 adds request validation and centralized API exception handling.

## Validation

`EmployeeRequest` uses Bean Validation:

- `name`: `@NotBlank`, `@Size(2-100)`
- `email`: `@NotBlank`, `@Email`, `@Size(max=150)`
- `departmentId`: `@NotNull`

The controller uses `@Valid` and `BindingResult`.

## Exception handling

`@RestControllerAdvice` returns consistent JSON responses for:

- Employee or department not found: **404 Not Found**
- Validation errors: **400 Bad Request** with field messages
- Malformed JSON or wrong data format: **400 Bad Request**

## Run

```bash
mvn spring-boot:run
```

## Examples

Valid request:

```bash
curl -X POST http://localhost:8080/api/employees \
  -H 'Content-Type: application/json' \
  -d '{"name":"Nguyen Van A","email":"a@example.com","departmentId":1}'
```

Invalid request:

```bash
curl -i -X POST http://localhost:8080/api/employees \
  -H 'Content-Type: application/json' \
  -d '{"name":"","email":"invalid","departmentId":null}'
```

Missing employee:

```bash
curl -i http://localhost:8080/api/employees/999
```
