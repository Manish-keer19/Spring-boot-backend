# Complete Spring Boot Master Guide - 9 Learning Phases

## Phase 1: Core Spring Fundamentals

### ✅ Key Concepts

**Inversion of Control (IoC)**
- The principle where object creation and dependency management is handled by the Spring container rather than the object itself
- Reduces coupling between classes and increases testability

**Dependency Injection (DI)**
- The process of providing dependencies to an object from external sources
- Three types: Constructor Injection, Setter Injection, Field Injection

**Bean Lifecycle**
- Instantiation → Dependency Injection → Post-processors → Initialization → Usage → Destruction
- Spring manages the complete lifecycle of beans

**ApplicationContext**
- The Spring IoC container that manages beans
- Provides configuration metadata and manages bean relationships

### 🔍 Code Examples

```java
// Configuration Class
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
    
    @Bean
    @Scope("singleton")
    public UserService userService() {
        return new UserServiceImpl();
    }
}

// Service with Constructor Injection
@Service
public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User findById(Long id) {
        return userRepository.findById(id);
    }
}

// Repository Implementation
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    @Override
    public User findById(Long id) {
        // Database logic here
        return new User(id, "John Doe");
    }
}

// Bean Lifecycle Methods
@Component
public class LifecycleBean {
    
    @PostConstruct
    public void init() {
        System.out.println("Bean initialized");
    }
    
    @PreDestroy
    public void cleanup() {
        System.out.println("Bean destroyed");
    }
}
```

### 📌 Best Practices and Common Pitfalls

**Best Practices:**
- Prefer constructor injection over field injection for mandatory dependencies
- Use `@Component`, `@Service`, `@Repository`, `@Controller` for semantic clarity
- Keep configuration classes focused and organized
- Use profiles for environment-specific configurations

**Common Pitfalls:**
- Circular dependencies - solved by using `@Lazy` or refactoring design
- Forgetting `@ComponentScan` in configuration classes
- Using field injection making testing difficult
- Not understanding bean scopes leading to unexpected behavior

### ✅ Learning Checklist

- [ ] Understand IoC and DI principles
- [ ] Create and configure beans using annotations and Java config
- [ ] Implement all three types of dependency injection
- [ ] Understand bean scopes (singleton, prototype, request, session)
- [ ] Work with ApplicationContext and BeanFactory
- [ ] Implement bean lifecycle methods
- [ ] Handle circular dependencies
- [ ] Use Spring profiles for different environments

### 💡 Mini Project Ideas

1. **Employee Management System Core**: Build a basic employee management system with User, Department, and Employee entities. Implement proper dependency injection between services and repositories without any web layer.

2. **Library Book Tracker**: Create a console-based library system that manages books, authors, and borrowers. Focus on proper IoC container configuration and bean lifecycle management.

---

## Phase 2: Spring Boot Essentials

### ✅ Key Concepts

**Spring Boot Starters**
- Pre-configured dependency bundles that simplify project setup
- Common starters: web, data-jpa, security, test, actuator

**Auto-Configuration**
- Automatic configuration of Spring applications based on classpath dependencies
- Uses `@EnableAutoConfiguration` and conditional annotations

**Application Properties**
- External configuration through application.properties or application.yml
- Property binding with `@ConfigurationProperties` and `@Value`

**Spring Boot Profiles**
- Environment-specific configurations (dev, test, prod)
- Profile-specific property files and bean definitions

### 🔍 Code Examples

```java
// Main Application Class
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// Configuration Properties
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private String name;
    private String version;
    private Database database = new Database();
    
    @Data
    public static class Database {
        private String url;
        private String username;
        private String password;
    }
}

// Using Properties
@Service
public class ConfigService {
    
    @Value("${app.name:Default App}")
    private String appName;
    
    private final AppProperties appProperties;
    
    public ConfigService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }
    
    public void printConfig() {
        System.out.println("App: " + appName);
        System.out.println("DB URL: " + appProperties.getDatabase().getUrl());
    }
}

// Profile-specific Configuration
@Configuration
@Profile("dev")
public class DevConfig {
    
    @Bean
    @Primary
    public DataSource devDataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:h2:mem:devdb")
            .build();
    }
}

// Custom Auto-Configuration
@Configuration
@ConditionalOnClass(MyService.class)
@EnableConfigurationProperties(MyServiceProperties.class)
public class MyServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public MyService myService(MyServiceProperties properties) {
        return new MyService(properties);
    }
}
```

### 📌 Best Practices and Common Pitfalls

**Best Practices:**
- Use appropriate starters to minimize configuration
- Organize properties logically with prefixes
- Create custom configuration properties classes for complex configs
- Use profiles consistently across environments
- Leverage Spring Boot DevTools for development

**Common Pitfalls:**
- Over-configuring when auto-configuration would suffice
- Not understanding property precedence order
- Hardcoding environment-specific values
- Forgetting to enable configuration properties with `@EnableConfigurationProperties`
- Not using proper profile naming conventions

### ✅ Learning Checklist

- [ ] Create Spring Boot applications using Spring Initializr
- [ ] Understand and use common starters (web, data, security)
- [ ] Configure applications using properties and YAML files
- [ ] Implement custom configuration properties classes
- [ ] Use `@Value` and `@ConfigurationProperties` effectively
- [ ] Set up and use multiple profiles (dev, test, prod)
- [ ] Create custom auto-configuration classes
- [ ] Use Spring Boot DevTools for hot reloading
- [ ] Understand application startup process and customization

### 💡 Mini Project Ideas

1. **Multi-Environment Config Manager**: Build an application that demonstrates different configurations for dev, test, and production environments. Include database connections, logging levels, and feature flags that change based on active profiles.

2. **Custom Starter Creation**: Create a custom Spring Boot starter for email functionality that auto-configures email services based on properties, includes conditional beans, and can be easily imported into other projects.

---

## Phase 3: Web Development with Spring MVC

### ✅ Key Concepts

**Spring MVC Architecture**
- Model-View-Controller pattern implementation in Spring
- DispatcherServlet as the front controller
- Request processing flow: Request → Handler Mapping → Controller → View Resolver → Response

**Controllers and Request Mapping**
- `@Controller` and `@RestController` for handling HTTP requests
- Request mapping annotations: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- Path variables, request parameters, and request bodies

**Exception Handling**
- Global exception handling with `@ControllerAdvice`
- Custom exception classes and error responses
- Validation and error message handling

**Content Negotiation**
- JSON and XML response handling
- HTTP status codes and response entities
- File upload and download handling

### 🔍 Code Examples

```java
// REST Controller
@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<User> users = userService.findAll(page, size);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateUserRequest request) {
        
        User user = userService.update(id, request);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

// Request DTOs with Validation
@Data
public class CreateUserRequest {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;
    
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;
    
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age must not exceed 120")
    private Integer age;
}

// Global Exception Handler
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, WebRequest request) {
        
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error("Resource Not Found")
            .message(ex.getMessage())
            .path(request.getDescription(false))
            .build();
            
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
            
        ValidationErrorResponse errorResponse = ValidationErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .fieldErrors(errors)
            .build();
            
        return ResponseEntity.badRequest().body(errorResponse);
    }
}

// File Upload Controller
@RestController
@RequestMapping("/api/files")
public class FileController {
    
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile file) {
        
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }
        
        String fileName = fileService.store(file);
        FileUploadResponse response = new FileUploadResponse(fileName, file.getSize());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileService.loadAsResource(fileName);
        
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, 
                   "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
```

### 📌 Best Practices and Common Pitfalls

**Best Practices:**
- Use appropriate HTTP methods and status codes
- Implement proper input validation with Bean Validation
- Create separate DTOs for requests and responses
- Use global exception handling for consistent error responses
- Implement proper content negotiation
- Use ResponseEntity for better control over HTTP responses
- Follow RESTful API design principles

**Common Pitfalls:**
- Exposing entity classes directly in REST endpoints
- Not handling exceptions properly, leading to poor user experience
- Forgetting to validate input data
- Using wrong HTTP status codes
- Not implementing proper error response structure
- Mixing business logic in controllers
- Not handling file upload size limits and validation

### ✅ Learning Checklist

- [ ] Create REST controllers with proper request mappings
- [ ] Implement CRUD operations with appropriate HTTP methods
- [ ] Use path variables, query parameters, and request bodies
- [ ] Implement input validation with Bean Validation
- [ ] Create global exception handlers
- [ ] Handle file uploads and downloads
- [ ] Use ResponseEntity for custom responses
- [ ] Implement proper error response structure
- [ ] Understand content negotiation (JSON/XML)
- [ ] Test REST endpoints with proper HTTP clients

### 💡 Mini Project Ideas

1. **RESTful Blog API**: Create a complete blog REST API with posts, comments, and categories. Implement full CRUD operations, pagination, search functionality, input validation, and comprehensive exception handling.

2. **File Management Service**: Build a file management REST service that handles file uploads, downloads, metadata storage, file type validation, and thumbnail generation. Include proper error handling and file size restrictions.

---

## Phase 4: Data Access with Spring Data JPA and MongoDB

### ✅ Key Concepts

**Spring Data JPA**
- Abstraction layer over JPA for simplified data access
- Repository pattern implementation with built-in CRUD operations
- Query methods, custom queries, and specifications

**Entity Relationships**
- One-to-One, One-to-Many, Many-to-One, Many-to-Many mappings
- Cascade operations and fetch strategies
- Bidirectional relationships and ownership

**Database Transactions**
- `@Transactional` annotation and transaction management
- Transaction propagation and isolation levels
- Rollback strategies and exception handling

**Spring Data MongoDB**
- Document-based data access with MongoDB
- MongoTemplate and MongoRepository
- Aggregation pipelines and custom queries

### 🔍 Code Examples

```java
// JPA Entity
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

// JPA Repository
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    
    // Query methods
    Optional<User> findByEmail(String email);
    List<User> findByStatus(UserStatus status);
    List<User> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :start AND :end")
    List<User> findUsersCreatedBetween(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end);
    
    @Modifying
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    int updateUserStatus(@Param("id") Long id, @Param("status") UserStatus status);
    
    // Pagination and Sorting
    Page<User> findByStatus(UserStatus status, Pageable pageable);
}

// Service with Transactions
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    
    public UserService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }
    
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    public User createUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("User with email already exists");
        }
        
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .status(UserStatus.ACTIVE)
            .build();
            
        return userRepository.save(user);
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void deactivateUserAndCancelOrders(Long userId) {
        User user = findById(userId);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
        
        // Cancel all pending orders
        List<Order> pendingOrders = orderRepository.findByUserAndStatus(user, OrderStatus.PENDING);
        pendingOrders.forEach(order -> order.setStatus(OrderStatus.CANCELLED));
        orderRepository.saveAll(pendingOrders);
    }
}

// MongoDB Document
@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String sku;
    
    private String name;
    private String description;
    private BigDecimal price;
    
    @DBRef
    private Category category;
    
    private List<String> tags;
    private Map<String, Object> attributes;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

// MongoDB Repository
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
    List<Product> findByCategory(Category category);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findByTagsContaining(String tag);
    
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Product> findByNameRegex(String name);
    
    @Aggregation(pipeline = {
        "{ $match: { 'category': ?0 } }",
        "{ $group: { _id: '$category', avgPrice: { $avg: '$price' }, count: { $sum: 1 } } }"
    })
    List<CategoryStatistics> getCategoryStatistics(String categoryId);
}

// Custom Repository Implementation
@Component
public class CustomProductRepositoryImpl implements CustomProductRepository {
    
    private final MongoTemplate mongoTemplate;
    
    public CustomProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    @Override
    public List<Product> findProductsWithComplexQuery(ProductSearchCriteria criteria) {
        Query query = new Query();
        
        if (criteria.getName() != null) {
            query.addCriteria(Criteria.where("name").regex(criteria.getName(), "i"));
        }
        
        if (criteria.getMinPrice() != null && criteria.getMaxPrice() != null) {
            query.addCriteria(Criteria.where("price").gte(criteria.getMinPrice()).lte(criteria.getMaxPrice()));
        }
        
        if (criteria.getTags() != null && !criteria.getTags().isEmpty()) {
            query.addCriteria(Criteria.where("tags").in(criteria.getTags()));
        }
        
        return mongoTemplate.find(query, Product.class);
    }
}
```

### 📌 Best Practices and Common Pitfalls

**Best Practices:**
- Use appropriate fetch strategies (LAZY vs EAGER) based on use case
- Implement proper transaction boundaries in service layer
- Use pagination for large datasets
- Create proper indexes for frequently queried fields
- Use projections and DTOs to avoid over-fetching
- Implement proper exception handling for data access
- Use `@Transactional(readOnly = true)` for read operations

**Common Pitfalls:**
- N+1 query problem with lazy loading
- Not using transactions properly leading to data inconsistency
- Fetching too much data without pagination
- Not handling OptionalEmpty properly
- Using entities directly in web layer
- Forgetting to handle database constraint violations
- Not optimizing queries leading to performance issues
- Mixing JPA and native queries unnecessarily

### ✅ Learning Checklist

- [ ] Create JPA entities with proper relationships and mappings
- [ ] Implement repositories with custom query methods
- [ ] Use specifications for dynamic queries
- [ ] Implement proper transaction management
- [ ] Handle pagination and sorting
- [ ] Create custom repository implementations
- [ ] Work with MongoDB documents and repositories
- [ ] Implement aggregation pipelines
- [ ] Use projections and DTOs for optimal data transfer
- [ ] Handle database exceptions and constraints
- [ ] Optimize queries and avoid N+1 problems
- [ ] Use database migrations with Flyway or Liquibase

### 💡 Mini Project Ideas

1. **E-commerce Product Catalog**: Build a product catalog system using both JPA (for orders, users) and MongoDB (for products, reviews). Implement complex queries, relationships, transactions, and search functionality with proper data modeling.

2. **Social Media Analytics Dashboard**: Create a system that tracks social media posts, user interactions, and generates analytics. Use JPA for user management and MongoDB for storing posts and interaction data with aggregation pipelines for analytics.

---

## Phase 5: Spring Security

### ✅ Key Concepts

**Authentication vs Authorization**
- Authentication: Verifying user identity (who you are)
- Authorization: Determining user permissions (what you can do)
- Principal, Authorities, and Security Context

**Spring Security Architecture**
- Security Filter Chain and Filter Security Interceptor
- Authentication Manager and Authentication Providers
- UserDetailsService and UserDetails interface

**JWT (JSON Web Tokens)**
- Stateless authentication mechanism
- Token structure: Header.Payload.Signature
- Token generation, validation, and refresh strategies

**OAuth2 and Role-Based Access Control**
- OAuth2 authorization framework
- Resource servers and authorization servers
- Role hierarchy and method security

### 🔍 Code Examples

```java
// Security Configuration
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );
            
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}

// User Entity with Security
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}

// Custom UserDetailsService
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
            
        return UserPrincipal.create(user);
    }
}

// JWT Utility Class
@Component
public class JwtTokenUtil {
    
    private static final String SECRET = "mySecret";
    private static final int JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours
    
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        claims.put("authorities", authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        return createToken(claims, userDetails.getUsername());
    }
    
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

// Authentication Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticate(request.getUsername(), request.getPassword());
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername(), 
            userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())));
    }
    
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody SignUpRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse("Username is already taken!"));
        }
        
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse("Email is already in use!"));
        }
        
        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
            
        Set<String> strRoles = request.getRoles();
        Set<Role> roles = new HashSet<>();
        
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("User Role not set."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Admin Role not set."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("User Role not set."));
                        roles.add(userRole);
                }
            });
        }
        
        user.setRoles(roles);
        userService.save(user);
        
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    
    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}

// JWT Request Filter
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    
    public JwtRequestFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws ServletException, IOException {
        
        final String requestTokenHeader = request.getHeader("Authorization");
        
        String username = null;
        String jwtToken = null;
        
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired");
            }
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }
}

// Method Security Examples
@Service
public class ProductService {
    
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(CreateProductRequest request) {
        // Only admins can create products
        return productRepository.save(new Product(request));
    }
    
    @PreAuthorize("hasRole('ADMIN') or @productService.isOwner(#productId, authentication.name)")
    public Product updateProduct(Long productId, UpdateProductRequest request) {
        // Admins or product owners can update
        return productRepository.save(updatedProduct);
    }
    
    @PostAuthorize("hasRole('ADMIN') or returnObject.isPublic()")
    public Product getProduct(Long productId) {
        // Return product only if user is admin or product is public
        return productRepository.findById(productId);
    }
    
    public boolean isOwner(Long productId, String username) {
        return productRepository.findById(productId)
            .map(product -> product.getOwner().getUsername().equals(username))
            .orElse(false);
    }
}
```

### 📌 Best Practices and Common Pitfalls

**Best Practices:**
- Always encode passwords using strong hashing algorithms (BCrypt)
- Use JWT tokens with appropriate expiration times
- Implement proper role-based access control
- Use HTTPS in production for secure token transmission
- Store sensitive configuration in environment variables
- Implement proper logout functionality to invalidate tokens
- Use method-level security for fine-grained control

**Common Pitfalls:**
- Storing passwords in plain text
- Using weak JWT secrets or not rotating them
- Not implementing proper token expiration and refresh
- Exposing sensitive endpoints without authentication
- Not handling authentication exceptions properly
- Using overly broad security rules
- Not implementing CSRF protection where needed
- Mixing authentication logic with business logic

### ✅ Learning Checklist

- [ ] Configure Spring Security with custom authentication
- [ ] Implement JWT-based authentication and authorization
- [ ] Create custom UserDetailsService and UserDetails
- [ ] Set up role-based access control
- [ ] Implement method-level security with @PreAuthorize/@PostAuthorize
- [ ] Handle authentication and authorization exceptions
- [ ] Create secure password encoding and validation
- [ ] Implement proper logout and token invalidation
- [ ] Configure CORS and CSRF protection
- [ ] Set up OAuth2 integration
- [ ] Implement refresh token mechanism
- [ ] Create security testing strategies

### 💡 Mini Project Ideas

1. **Secure Banking API**: Build a banking API with multiple user roles (customer, manager, admin) where customers can view their accounts, managers can approve loans, and admins can manage all users. Implement JWT authentication, method security, and audit logging.

2. **Multi-tenant SaaS Application**: Create a multi-tenant application where users can only access data from their organization. Implement tenant-based security, role hierarchy, and resource-level access control with JWT tokens containing tenant information.

---

## Phase 6: Spring AOP (Aspect-Oriented Programming)

### ✅ Key Concepts

**Cross-Cutting Concerns**
- Functionalities that span multiple modules (logging, security, caching)
- Separation of concerns from business logic
- Examples: auditing, performance monitoring, exception handling

**AOP Terminology**
- **Aspect**: Module that encapsulates cross-cutting concerns
- **Join Point**: Point in program execution where aspect can be applied
- **Pointcut**: Expression that defines where advice should be applied
- **Advice**: Action taken by aspect (before, after, around, etc.)
- **Weaving**: Process of linking aspects with target objects

**Types of Advice**
- `@Before`: Executes before method execution
- `@After`: Executes after method execution (regardless of outcome)
- `@AfterReturning`: Executes after successful method execution
- `@AfterThrowing`: Executes after method throws exception
- `@Around`: Surrounds method execution

### 🔍 Code Examples

```java
// Logging Aspect
@Aspect
@Component
@Slf4j
public class LoggingAspect {
    
    // Pointcut for all service methods
    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceMethods() {}
    
    // Pointcut for all repository methods
    @Pointcut("execution(* com.example.repository.*.*(..))")
    public void repositoryMethods() {}
    
    // Pointcut for methods annotated with @Loggable
    @Pointcut("@annotation(com.example.annotation.Loggable)")
    public void loggableMethodsPointcut() {}
    
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Executing method: {}.{} with arguments: {}", 
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName(),
            Arrays.toString(joinPoint.getArgs()));
    }
    
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Method {}.{} executed successfully with result: {}", 
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName(),
            result);
    }
    
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        log.error("Method {}.{} threw exception: {}", 
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName(),
            exception.getMessage(), exception);
    }
    
    @Around("loggableMethodsPointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.info("Method {}.{} executed in {} ms", 
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                executionTime);
                
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("Method {}.{} failed after {} ms with exception: {}", 
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                executionTime,
                e.getMessage());
            throw e;
        }
    }
}

// Security Aspect
@Aspect
@Component
public class SecurityAspect {
    
    @Before("@annotation(requiresPermission)")
    public void checkPermission(JoinPoint joinPoint, RequiresPermission requiresPermission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User not authenticated");
        }
        
        String requiredPermission = requiresPermission.value();
        boolean hasPermission = authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals(requiredPermission));
            
        if (!hasPermission) {
            throw new AccessDeniedException("Insufficient permissions: " + requiredPermission);
        }
    }
}

// Audit Aspect
@Aspect
@Component
public class AuditAspect {
    
    private final AuditService auditService;
    
    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }
    
    @AfterReturning(pointcut = "@annotation(auditable)", returning = "result")
    public void auditMethod(JoinPoint joinPoint, Auditable auditable, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "anonymous";
        
        AuditLog auditLog = AuditLog.builder()
            .action(auditable.action())
            .entityType(auditable.entityType())
            .entityId(extractEntityId(args))
            .username(username)
            .methodName(className + "." + methodName)
            .timestamp(LocalDateTime.now())
            .details(createAuditDetails(args, result))
            .build();
            
        auditService.saveAuditLog(auditLog);
    }
    
    private String extractEntityId(Object[] args) {
        // Logic to extract entity ID from method arguments
        for (Object arg : args) {
            if (arg instanceof Long) {
                return arg.toString();
            }
            // Add more extraction logic as needed
        }
        return null;
    }
    
    private String createAuditDetails(Object[] args, Object result) {
        // Create detailed audit information
        return String.format("Arguments: %s, Result: %s", 
            Arrays.toString(args), result);
    }
}

// Caching Aspect
@Aspect
@Component
public class CachingAspect {
    
    private final CacheManager cacheManager;
    
    public CachingAspect(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
    
    @Around("@annotation(cacheable)")
    public Object cacheMethod(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        String cacheKey = generateCacheKey(joinPoint, cacheable.key());
        Cache cache = cacheManager.getCache(cacheable.value());
        
        if (cache != null) {
            Cache.ValueWrapper valueWrapper = cache.get(cacheKey);
            if (valueWrapper != null) {
                log.debug("Cache hit for key: {}", cacheKey);
                return valueWrapper.get();
            }
        }
        
        log.debug("Cache miss for key: {}", cacheKey);
        Object result = joinPoint.proceed();
        
        if (cache != null && result != null) {
            cache.put(cacheKey, result);
            log.debug("Cached result for key: {}", cacheKey);
        }
        
        return result;
    }
    
    private String generateCacheKey(ProceedingJoinPoint joinPoint, String keyExpression) {
        if (keyExpression.isEmpty()) {
            return joinPoint.getSignature().getName() + ":" + 
                   Arrays.toString(joinPoint.getArgs());
        }
        
        // Implement SpEL evaluation for custom key expressions
        return evaluateKeyExpression(keyExpression, joinPoint);
    }
    
    private String evaluateKeyExpression(String keyExpression, ProceedingJoinPoint joinPoint) {
        // SpEL evaluation logic
        return keyExpression; // Simplified for example
    }
}

// Custom Annotations
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    String value() default "";
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {
    String value();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    String action();
    String entityType();
}

// Performance Monitoring Aspect
@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    
    private final MeterRegistry meterRegistry;
    
    public PerformanceAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    
    @Around("execution(* com.example.service.*.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        Timer timer = Timer.builder("method.execution.time")
            .tag("class", className)
            .tag("method", methodName)
            .register(meterRegistry);
            
        return timer.recordCallable(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                meterRegistry.counter("method.execution.error", 
                    "class", className, 
                    "method", methodName,
                    "exception", throwable.getClass().getSimpleName())
                    .increment();
                throw throwable;
            }
        });
    }
}

// Service with AOP Annotations
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Loggable
    @Auditable(action = "CREATE_USER", entityType = "USER")
    @RequiresPermission("USER_CREATE")
    public User createUser(CreateUserRequest request) {
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .build();
            
        return userRepository.save(user);
    }
    
    @Cacheable(value = "users", key = "#id")
    @Loggable
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    @Auditable(action = "UPDATE_USER", entityType = "USER")
    @CacheEvict(value = "users", key = "#id")
    public User updateUser(Long id, UpdateUserRequest request) {
        User user = findById(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }
}
```

### 📌 Best Practices and Common Pitfalls

**Best Practices:**
- Keep aspects focused on single cross-cutting concerns
- Use appropriate advice types for specific use cases
- Write clear and specific pointcut expressions
- Handle exceptions properly in aspects
- Use custom annotations for better readability and maintainability
- Consider performance impact of aspects, especially with @Around advice
- Document aspect behavior and dependencies

**Common Pitfalls:**
- Creating overly broad pointcuts that match unintended methods
- Not handling exceptions in @Around advice
- Forgetting to call `joinPoint.proceed()` in @Around advice
- Creating circular dependencies between aspects and beans
- Not considering the order of aspect execution
- Using aspects for business logic instead of cross-cutting concerns
- Poor performance due to complex pointcut expressions

### ✅ Learning Checklist

- [ ] Understand AOP concepts and terminology
- [ ] Create custom aspects with different advice types
- [ ] Write effective pointcut expressions
- [ ] Use @Around advice for method interception
- [ ] Create custom annotations for aspect targeting
- [ ] Implement logging and auditing aspects
- [ ] Build performance monitoring aspects
- [ ] Handle exceptions in aspects properly
- [ ] Configure aspect ordering with @Order
- [ ] Understand proxy-based AOP limitations
- [ ] Use SpEL expressions in pointcuts
- [ ] Test aspect behavior effectively

### 💡 Mini Project Ideas

1. **Comprehensive Audit System**: Build an audit system that tracks all CRUD operations, user actions, and system events. Include aspects for method execution time, security checks, data change tracking, and automatic report generation.

2. **API Rate Limiting and Monitoring**: Create an AOP-based rate limiting system that monitors API usage, implements different rate limits for different user tiers, logs violations, and provides real-time metrics dashboard.

---

## Phase 7: Testing with JUnit & Mockito

### ✅ Key Concepts

**Testing Pyramid**
- Unit Tests: Test individual components in isolation
- Integration Tests: Test component interactions
- End-to-End Tests: Test complete user workflows

**Spring Boot Testing Annotations**
- `@SpringBootTest`: Loads full application context
- `@WebMvcTest`: Tests web layer components
- `@DataJpaTest`: Tests JPA repositories
- `@TestConfiguration`: Custom test configurations

**Mockito Framework**
- Mock objects for dependencies
- Stubbing method behaviors
- Verification of method invocations
- Argument matchers and capturing

**Test Slices**
- Focused testing of specific layers
- Faster execution and isolated concerns
- Reduced context loading overhead

### 🔍 Code Examples

```java
// Unit Test for Service Layer
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private EmailService emailService;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .name("John Doe")
            .email("john@example.com")
            .build();
            
        User savedUser = User.builder()
            .id(1L)
            .name("John Doe")
            .email("john@example.com")
            .build();
            
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // When
        User result = userService.createUser(request);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john@example.com");
        
        verify(userRepository).existsByEmail(request.getEmail());
        verify(userRepository).save(any(User.class));
        verify(emailService).sendWelcomeEmail("john@example.com");
    }
    
    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .name("John Doe")
            .email("john@example.com")
            .build();
            
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
        
        // When & Then
        assertThatThrownBy(() -> userService.createUser(request))
            .isInstanceOf(DuplicateResourceException.class)
            .hasMessage("User with email already exists");
        
        verify(userRepository).existsByEmail(request.getEmail());
        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendWelcomeEmail(anyString());
    }
    
    @Test
    @DisplayName("Should find user by id")
    void shouldFindUserById() {
        // Given
        Long userId = 1L;
        User user = User.builder().id(userId).name("John Doe").build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        // When
        User result = userService.findById(userId);
        
        // Then
        assertThat(result).isEqualTo(user);
        verify(userRepository).findById(userId);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "a", "ab"})
    @DisplayName("Should throw exception for invalid names")
    void shouldThrowExceptionForInvalidNames(String invalidName) {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .name(invalidName)
            .email("john@example.com")
            .build();
        
        // When & Then
        assertThatThrownBy(() -> userService.createUser(request))
            .isInstanceOf(IllegalArgumentException.class);
    }
}

// Web Layer Testing
@WebMvcTest(UserController.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DisplayName("Should return user when valid id provided")
    void shouldReturnUserWhenValidIdProvided() throws Exception {
        // Given
        Long userId = 1L;
        User user = User.builder()
            .id(userId)
            .name("John Doe")
            .email("john@example.com")
            .build();
            
        when(userService.findById(userId)).thenReturn(user);
        
        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(userId))
            .andExpected(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.email").value("john@example.com"));
        
        verify(userService).findById(userId);
    }
    
    @Test
    @DisplayName("Should return 404 when user not found")
    void shouldReturn404WhenUserNotFound() throws Exception {
        // Given
        Long userId = 999L;
        when(userService.findById(userId))
            .thenThrow(new ResourceNotFoundException("User not found"));
        
        // When & Then
        mockMvc.perform(get("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Resource Not Found"));
    }
    
    @Test
    @DisplayName("Should create user with valid data")
    void shouldCreateUserWithValidData() throws Exception {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .name("John Doe")
            .email("john@example.com")
            .build();
            
        User createdUser = User.builder()
            .id(1L)
            .name("John Doe")
            .email("john@example.com")
            .build();
            
        when(userService.createUser(any(CreateUserRequest.class))).thenReturn(createdUser);
        
        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("John Doe"));
        
        verify(userService).createUser(any(CreateUserRequest.class));
    }
    
    @Test
    @DisplayName("Should return 400 for invalid input")
    void shouldReturn400ForInvalidInput() throws Exception {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .name("")  // Invalid name
            .email("invalid-email")  // Invalid email
            .build();
        
        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldErrors.name").exists())
            .andExpect(jsonPath("$.fieldErrors.email").exists());
        
        verify(userService, never()).createUser(any(CreateUserRequest.class));
    }
}

// Repository Testing
@DataJpaTest
class UserRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("Should find user by email")
    void shouldFindUserByEmail() {
        // Given
        User user = User.builder()
            .name("John Doe")
            .email("john@example.com")
            .build();
        entityManager.persistAndFlush(user);
        
        // When
        Optional<User> found = userRepository.findByEmail("john@example.com");
        
        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("John Doe");
    }
    
    @Test
    @DisplayName("Should return empty when user not found by email")
    void shouldReturnEmptyWhenUserNotFoundByEmail() {
        // When
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");
        
        // Then
        assertThat(found).isEmpty();
    }
    
    @Test
    @DisplayName("Should find users by status")
    void shouldFindUsersByStatus() {
        // Given
        User activeUser = User.builder()
            .name("Active User")
            .email("active@example.com")
            .status(UserStatus.ACTIVE)
            .build();
            
        User inactiveUser = User.builder()
            .name("Inactive User")
            .email("inactive@example.com")
            .status(UserStatus.INACTIVE)
            .build();
            
        entityManager.persist(activeUser);
        entityManager.persist(inactiveUser);
        entityManager.flush();
        
        // When
        List<User> activeUsers = userRepository.findByStatus(UserStatus.ACTIVE);
        
        // Then
        assertThat(activeUsers).hasSize(1);
        assertThat(activeUsers.get(0).getName()).isEqualTo("Active User");
    }
}

// Integration Testing
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceIntegrationTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @MockBean
    private EmailService emailService;
    
    @Test
    @DisplayName("Should create and retrieve user successfully")
    @Transactional
    @Rollback
    void shouldCreateAndRetrieveUserSuccessfully() {
        // Given
        CreateUserRequest request = CreateUserRequest.builder()
            .name("Integration Test User")
            .email("integration@example.com")
            .build();
        
        // When
        User createdUser = userService.createUser(request);
        User retrievedUser = userService.findById(createdUser.getId());
        
        // Then
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getName()).isEqualTo("Integration Test User");
        assertThat(retrievedUser.getEmail()).isEqualTo("integration@example.com");
        
        verify(emailService).sendWelcomeEmail("integration@example.com");
    }
}

// Custom Test Configuration
@TestConfiguration
public class TestConfig {
    
    @Bean
    @Primary
    public Clock testClock() {
        return Clock.fixed(Instant.parse("2023-01-01T12:00:00Z"), ZoneOffset.UTC);
    }
    
    @Bean
    @Primary
    public EmailService mockEmailService() {
        return Mockito.mock(EmailService.class);
    }
}

// Security Testing
@SpringBootTest
@AutoConfigureTestDatabase
class SecurityIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("Should require authentication for protected endpoints")
    void shouldRequireAuthenticationForProtectedEndpoints() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity("/api/users", String.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
    
    @Test
    @DisplayName("Should allow access with valid JWT token")
    void shouldAllowAccessWithValidJwtToken() {
        // Given
        String token = createValidJwtToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        // When
        ResponseEntity<String> response = restTemplate.exchange(
            "/api/users", HttpMethod.GET, entity, String.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(