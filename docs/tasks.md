# Improvement Tasks Checklist

An ordered, actionable checklist to evolve the MedicalAppointmentAPI. Each item is intentionally small and testable. Check off [x] as you complete.

1. [ ] Establish contribution and coding standards
   - [ ] Add CONTRIBUTING.md (branch strategy, commit style, PR checklist)
   - [ ] Add code style configuration (EditorConfig + Checkstyle/Spotless)
   - [ ] Define package and layering conventions (controller → service → repository → entity)

2. [ ] Introduce API documentation and discoverability
   - [ ] Add springdoc-openapi starter dependency
   - [ ] Generate OpenAPI spec and enable Swagger UI
   - [ ] Annotate controllers/DTOs with operation/parameter/response docs

3. [ ] Enforce layered architecture boundaries
   - [ ] Remove direct repository exposure from controllers (e.g., AppointmentController)
   - [ ] Ensure controllers depend only on services and DTOs (no entities/repositories)
   - [ ] Validate request shape at the boundary (Jakarta Bean Validation + @Valid)

4. [ ] Standardize error handling and error response model
   - [ ] Review and unify RequestErrorException vs RequestNotFoundException semantics
   - [ ] Ensure RequestExceptionHandler maps exceptions to consistent HTTP status codes and body (problem+json-like)
   - [ ] Add validation error response formatting (field-level messages, codes)

5. [ ] Strengthen transactional integrity
   - [ ] Add @Transactional to service methods that change multiple aggregates (e.g., saveAppointment toggling availability and saving appointment)
   - [ ] Ensure repository save operations occur within a single atomic transaction
   - [ ] Consider propagation rules for nested operations

6. [ ] Improve availability overlap and date-time handling
   - [ ] Refine DoctorAvailabilityRequestValidation.availabilityValidation to account for inclusive bounds and equal endpoints
   - [ ] Add normalization/timezone strategy (document assumption, use UTC or ZonedDateTime)
   - [ ] Validate that start < end and block zero/negative duration
   - [ ] Add repository-level queries to efficiently detect overlap instead of iterating all in memory

7. [ ] Introduce mapping layer for DTO ↔ entity
   - [ ] Add MapStruct (or similar) mappers for Appointment, Doctor, Patient, DoctorAvailability
   - [ ] Replace manual builder methods in services with mappers
   - [ ] Centralize enum conversions and handle invalid values predictably

8. [ ] Add pagination, sorting, and filtering to list endpoints
   - [ ] Replace findAll with Page/Sort variants (Spring Data Pageable)
   - [ ] Validate and sanitize pagination parameters
   - [ ] Reflect pagination metadata in response

9. [ ] Harden service methods and repository usage
   - [ ] Replace custom findXById methods returning null with Optional-based patterns or orElseThrow
   - [ ] Consolidate retrieval into helper methods with consistent exception behavior
   - [ ] Avoid multiple redundant database hits by batching or eager fetching where appropriate

10. [ ] Review entity relationships and invariants
    - [ ] Ensure bidirectional relations are synchronized (e.g., availability ↔ appointment)
    - [ ] Add cascade and orphanRemoval settings only where needed
    - [ ] Add unique constraints and indexes (e.g., CRM, CPF, appointment-availability uniqueness)

11. [ ] Validation improvements at DTO level
    - [ ] Add Bean Validation annotations to request DTOs (e.g., @NotNull, @Email, @Pattern for CPF/CRM, @Future for dates)
    - [ ] Add group-based validations for create vs update DTOs
    - [ ] Enforce enum inputs with @Enum constraint or custom validator

12. [ ] Controller semantics and HTTP correctness
    - [ ] Use proper status codes (201 Created with Location header for POST; 204 No Content without body for DELETE)
    - [ ] Align endpoint paths to RESTful conventions (POST /appointments; GET /appointments/{id}; PATCH for partial updates)
    - [ ] Validate and sanitize query parameters for /search endpoints; consider replacing with canonical resource paths

13. [ ] Logging and observability
    - [ ] Add structured logging (request IDs, user/session IDs when applicable)
    - [ ] Introduce basic metrics (request counts, latencies, error rates) with Micrometer
    - [ ] Add traceability (correlation IDs) and MDC propagation

14. [ ] Security baseline (if applicable)
    - [ ] Add Spring Security dependency and minimal config (deny-all by default, open selected endpoints)
    - [ ] Plan for authentication/authorization (JWT/OAuth2) and roles (doctor, patient, admin)
    - [ ] Validate inputs to prevent injection attacks (method-level and repository-level)

15. [ ] Database migrations and environment parity
    - [ ] Add Flyway/Liquibase with initial baseline schema
    - [ ] Version schema changes (constraints, indexes)
    - [ ] Provide sample data migrations for local/dev profiles

16. [ ] Configuration management and profiles
    - [ ] Externalize secrets and sensitive configs; remove from application.yml
    - [ ] Add separate profiles (dev, test, prod) with overrides
    - [ ] Document configuration parameters in README

17. [ ] Testing strategy and coverage
    - [ ] Add unit tests for services (including validation and edge cases for overlap)
    - [ ] Add slice tests for repositories (data access, queries)
    - [ ] Add WebMvc/WebFlux tests for controllers with MockMvc
    - [ ] Add integration tests booting the context (Testcontainers for DB)
    - [ ] Introduce test data builders/fixtures

18. [ ] Continuous Integration and quality gates
    - [ ] Add GitHub Actions (or similar) workflow: build, test, static analysis
    - [ ] Add code coverage with JaCoCo and enforce minimum thresholds
    - [ ] Add static analysis (SpotBugs/PMD) and style checks (Checkstyle/Spotless)

19. [ ] Performance and scalability review
    - [ ] Assess N+1 issues and add fetch strategies or DTO projections
    - [ ] Add indexes for frequent queries (doctorId, patientId, date ranges)
    - [ ] Consider caching frequently read reference data (doctors, specialties)

20. [ ] DTOs and serialization
    - [ ] Ensure JSON serialization uses ISO-8601 for dates; configure Jackson JavaTimeModule
    - [ ] Validate null handling and default values in responses
    - [ ] Avoid exposing internal entity IDs where not needed; use opaque identifiers if required later

21. [ ] Domain invariants for appointments
    - [ ] Ensure appointment status transitions are valid (e.g., SCHEDULED → COMPLETED/CANCELLED)
    - [ ] Prevent double-booking of doctor or patient within overlapping intervals
    - [ ] Free availability slot automatically on cancellation (idempotent behavior)

22. [ ] Idempotency and concurrency controls
    - [ ] Add optimistic locking (version fields) for entities updated concurrently (availability, appointment)
    - [ ] Consider idempotency keys for POST operations
    - [ ] Clarify conflict responses (HTTP 409) on booking races

23. [ ] API versioning and deprecation policy
    - [ ] Introduce versioned paths or media types (e.g., /api/v1)
    - [ ] Document deprecation process and support window

24. [ ] Packaging and deployment
    - [ ] Add Dockerfile and containerize application
    - [ ] Provide docker-compose with database for local dev
    - [ ] Document health/readiness endpoints and add Spring Boot Actuator

25. [ ] Documentation improvements
    - [ ] Update README with architecture overview and diagrams
    - [ ] Describe main flows (create availability, book appointment, cancel)
    - [ ] Add ADRs (Architecture Decision Records) for key choices (transactions, time zones, validation strategy)

26. [ ] Cleanup and refactors
    - [ ] Remove dead code and unused fields (e.g., unused AppointmentRepository in AppointmentController)
    - [ ] Rename ambiguous methods (findXById to findById returning Optional)
    - [ ] Normalize method/field naming across layers

27. [ ] Monitoring and runtime diagnostics
    - [ ] Add Actuator endpoints (health, metrics, info, prometheus)
    - [ ] Add basic alerts for error rates and latency budgets
    - [ ] Log slow queries and add timeouts at repository/service layer

28. [ ] Localization and message management
    - [ ] Externalize user-facing messages to message bundles
    - [ ] Provide i18n support hooks (if future requirement)

29. [ ] Data privacy and retention (forward-looking)
    - [ ] Identify PII (CPF, email, phone) and document handling policies
    - [ ] Add data masking in logs and responses where appropriate
    - [ ] Define retention and deletion policies for appointments and patients

30. [ ] Backward compatibility and migration plan
    - [ ] Plan incremental rollout of changes (feature flags)
    - [ ] Provide migration scripts and fallback procedures
    - [ ] Add smoke tests for critical endpoints post-deployment
