# 🌐 Spring-Project
Spring Boot 웹사이트 (개인 프로젝트)

<br>

## 🖥️ 프로젝트 소개
스프링 부트 + JPA를 활용한 웹 쇼핑몰 입니다.

<br>

> 📺 🎥 [기능 구현 영상](https://drive.google.com/file/d/1vrDuOA7JlgbuLWsEo7FczTv3B0jNf8ZF/view?usp=share_link)

<br>

## 📅 프로젝트 기간
22.12.14 ~ 22.12.26 (1인)

<br>

## ⌨️ 개발 환경
* OS : Mac
* Build : Gradle
* Tool : IntelliJ Ultimate , MySql WorkBench, Git
* Front-End : Html, CSS(BootStrap) , JavaSCript, Thymeleaf
* Back-End : Java(1.8.0) Spring Boot Spring Security, Spring Data JPA
* DataBase : MySQL

<br>

## 🔌 Dependencies
* Spring Boot DevTools
* Lombok
* Spring Data JPA
* Spring Security
* Spring Web
* Thymeleaf
* QueryDsl
* Spring Validaiton

## 🖱️ 기능 구현
1. 회원가입 및 로그인 
2. Form 조건 미달시 에러 메세지 출력
3. 게시판 기능
4. 상품 등록 및 수정, 삭제 기능
5. 상품 이미지 등록 기능
6. 식품 등록 및 수정, 삭제 기능
7. 상품 리스트 출력 및 검색 기능
8. 장바구니 기능
9. 주문 기능

<br>

## 💡 상세 기능 설명 💡

### 목차
* [로그인 기능](#-로그인-기능)
* [게시글 기능](#-로그인-기능)
* [상품 등록 및 수정 기능](#-상품-등록-및-수정-기능)
* [이미지 등록 및 수정 기능](#-이미지-등록-및-수정-기능)
* [상품 상세정보](#-상품-상세정보)
* [상품 주문 기능](#-상품-주문-기능)
* [장바구니 기능](#-장바구니-기능)
* [상품 취소 기능](#-상품-취소-기능)

## 📖 로그인 기능
![로그인 기능](https://user-images.githubusercontent.com/110580350/210503873-1abf4afb-e2d1-438c-9d09-84144bf7d885.gif)

<br>

### 회원가입
#### Controller
```java
@GetMapping("/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }
        //검증하는 객체아에서 @Valid 어노테이션 선언, 파라미터로 bindingResult 객체 추가 (검사 후 결과는 bindingResult 에 담아둠)
        //hasError()를 호출하여 에러가 있다면 다시 회원가입 페이지로 이동
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }
```
#### Service
```java
public Member saveMember(Member member){
        validateDuplicateMember(member); //회원 중복 메서드  
        return memberRepository.save(member); //Repository 에 member 저장 
    }

    public void validateDuplicateMember(Member member){ 
        Member findMember = memberRepository.findByEmail(member.getEmail());
        //JPA Repository 를 활용해 이메일 정보를 가져와 중복 확인
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }  
```

#### Status
```java
public enum Role {

    USER,ADMIN
}

member.setRole(Role.USER); //회원 가입할 때 상태를 USER로 저장
```

#### MemberForm
```java
//회원가입중에 해당 조건 미달 시 에러 메세지 출력
@NotBlank(message = "이름은 필수 입력 값입니다.")
private String name;
@NotEmpty(message = "이메일은 필수 입력 값입니다.")
@Email(message = "이메일 형식으로 입력해주세요")
private String email;
@NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
@Length(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요")
private String password;
@NotEmpty(message = "주소는 필수 입력 값입니다.")
private String address;
```
<br>

### 로그인

#### Controller
```java
@GetMapping("/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }
    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
```

#### Security
```java
@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login") //로그인 페이지 url 설정
                .defaultSuccessUrl("/") //로그인 성공 시 해당 url 반환
                .usernameParameter("email") //로그인 시 사용할 파라미터 이름으로 email 설정
                .failureUrl("/members/login/error") //로그인 실패 시 이동할 Url
                .and()
                .logout() //로그아웃 url
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/") //로그아웃 성공 시 이동할 url
        ;
      }
```

<br>

## 📖 게시글 기능

![게시글 기능](https://user-images.githubusercontent.com/110580350/210507419-4237c26f-bfc3-432e-9a85-4b0803942d91.gif)

### 게시글 등록

#### Controller
```java
 @GetMapping("/new")
    public String newArticleForm(Model model){
         model.addAttribute("articleFormDto",new ArticleFormDto());
         return "article/articleForm";
     }

     @PostMapping("/new")
    public String createArticle(@Valid ArticleFormDto articleFormDto, BindingResult bindingResult, Model model){

         if(bindingResult.hasErrors()){
             return "article/articleForm";
         }
         try{
            articleService.saveArticle(articleFormDto);
         }catch (Exception e){
             model.addAttribute("errorMessage","에러 발생");
             return "article/articleForm";
         }

         return "redirect:/article/index" ;
     }
```
#### Service

```java
public Long saveArticle(ArticleFormDto articleFormDto){

        Article article = articleFormDto.createArticle();
        articleRepository.save(article);

        return article.getId();
    }
```
<br>

### 게시글 수정 및 삭제

#### Controller

```java
  @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){

         try{
             Article articleEntity = articleRepository.findById(id).orElse(null);
             model.addAttribute("articleFormDto",articleEntity);
         }catch (Exception e){
             model.addAttribute("errorMessage","에러발생");
             model.addAttribute("articleFormDto", new ArticleFormDto());
             return "article/articleForm";
         }

         return "article/articleForm";
     }

     @PostMapping("/{id}/edit")
    public String update(@Valid ArticleFormDto articleFormDto, BindingResult bindingResult, Model model){

         if(bindingResult.hasErrors()){
             return "article/articleForm";
         }
         try{
             articleService.updateArticle(articleFormDto);
         }catch (Exception e){
             model.addAttribute("errorMessage","게시물 수정 중 오류가 발생했습니다.");
             return "article/articleForm";
         }
         return "redirect:/";
     }

     @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
         Article target = articleRepository.findById(id).orElse(null);

         if(target != null){
             articleRepository.delete(target);
         }
         return "redirect:/article/index";
     }
 ```
 
 #### Service
 
 ```java
 public Long updateArticle(ArticleFormDto articleFormDto) throws Exception{

        Article article = articleRepository.findById(articleFormDto.getId()).orElseThrow(EntityExistsException::new);
        article.updateArticle(articleFormDto);

        return article.getId();
    }
 ```
 
 ### 게시글 목록 및 상세 페이지
 
 ```java
 @GetMapping("/{articleId}")
    public String show(@PathVariable("articleId") Long id, Model model){

         try{
             Article articleEntity = articleRepository.findById(id).orElse(null);
             model.addAttribute("articleForm", articleEntity);
             System.out.println(id);
         }catch(Exception e){
             model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
             model.addAttribute("articleForm", new ArticleFormDto());
         }

         return "article/show";
     }

     @GetMapping("/index")
    public String index(Model model){
         List<Article> articleEntiyList = articleRepository.findAll();

         model.addAttribute("articleList",articleEntiyList);

         return "article/index";
     }
 ```
 
<br>

## 📖 상품 등록 및 수정 기능
![상품 등록](https://user-images.githubusercontent.com/110580350/210509320-672d7a0e-7678-4f2a-94c2-398aed9aaf16.gif)

### 상품 등록

#### Controller
```java
 @GetMapping("/admin/item/new")
    public String ItemForm(Model model){
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList){

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage","첫번 째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }

        try{
            itemService.saveItem(itemFormDto, itemImgFileList);
        }catch(Exception e){
            model.addAttribute("errorMessage","상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }
        return "redirect:/";
    }
```

#### Service

```java
public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0)
                itemImg.setRepImgYn("Y"); //첫번 쨰 이미지를 Y를 주고 대표이미지 설정
            else
                itemImg.setRepImgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
            //상품 이미지를 저장
        }

        return item.getId();
    }
```

<br>

### 상품 수정

#### Controller
```java
 @GetMapping("/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
        try{
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto",itemFormDto);
        }catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("itemFormDto",new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemForm";
    }
@PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){
        if(bindingResult.hasErrors()){
            return "item/itemForm";
        }
        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번 째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }
        try{
            itemService.updateItem(itemFormDto, itemImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage","상품 수정 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }
        return "redirect:/";
    }
```

#### Service
```java
 @Transactional(readOnly = true) //트랜젝션을 읽기 전용으로 설정
    //Jpa 가 더티체킹(변경 감지)을 수행하지 않아서 성능 향상
    public ItemFormDto getItemDtl(Long itemId){
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        //해당상품 이미지를 조회 / 등록 순으로 가지고 오기위해 상품 이미지 아이디를 오름차순으로 가지고옴
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for(ItemImg itemImg : itemImgList){ //조회한 ItemImg Entity 를 ItemImgDto 객체로 만들어서 리스트에 추가
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        //상품에 아이디를 통해 상품 엔티티를 조회            //존재하지 않으면 에외 발생
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }
 public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFielLIst) throws Exception{

        //상품수정
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        for(int i = 0; i < itemImgFielLIst.size(); i++){
            itemImgService.updateItemImg(itemImgIds.get(i),itemImgFielLIst.get(i));
        }
        return item.getId();
    }
```

<br>

## 📖 이미지 등록 및 수정 기능

```java 
 public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void  updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{

        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            //업데이트한 상품 이미지를 파일을 업로드
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }

    }
```

### WebMvcConfigure
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                //웹 브라우저에 입력하는 url 에 /images 로 시작하는 경우 uploadPath 에 설정한 다음으로 준으로 파일을 읽어 오도록 설정
                .addResourceLocations(uploadPath); //로컬컴퓨터에 저장된 파일을 읽어올 경로 설정

    }

}
```


<br>

## 📖 상품 상세정보
![상품 상세정보](https://user-images.githubusercontent.com/110580350/210510373-4be43025-df79-4f0b-86da-50cd799daa59.gif)


#### Controller
```java
 @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String pageable(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items",items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);

        //ItemController 클래스에 상품 관리 화면 이동 및 조회한 상품 데이터를 화면에 전달하는 로직을 구현
        //현재 상품 데이터가 많이 없는 관계로 한 페이지당 총 3개의 상품만 보여주록 설정함

        return "item/itemMng";

    }

    @GetMapping("/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        model.addAttribute("item",itemFormDto);
        return "item/itemDtl";
    }

    @GetMapping("/items") //상품 메인 페이지
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items",items);
        model.addAttribute("itemSearchDto",itemSearchDto);
        model.addAttribute("maxPage",5);

        return "item/items";
    }
```

#### Service
```java
 @Transactional(readOnly = true) //관리자만 보이는 상품 목록 기능
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }
    //ItemService 클래스에 상품 조회 조건과 페이지 정보를 파라미터로 받아서 상품 데이터를 조회하는 getAdminItemPage() 메소드를 추가
    //데이터의 수정이 일어나지 않으므로 최적화를 위해 @Transactional(readOnly = true) 어노테이션을 설정

    @Transactional(readOnly = true) //이용자에게 보이는 상품 페이지
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
```
<br>

## 📖 상품 주문 기능
![상품 주문 기능](https://user-images.githubusercontent.com/110580350/210510987-f9084231-ca17-408f-802d-e4877837dcc0.gif)

#### Controller
```java
 @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){
        //주문 정보를 받는 orderDto 객체에 데이터 바인딩 시 에러가 있는 지 검사
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        //현재 로그인 유저의 정보를 얻기위해 @Controller 어노테이션이 선언된 클래스에서 메소드 인자로 Principal 객체로 넘겨줄 경우 해당 객체에 접근할 수 있다.
        //Principal 객체에서 현재 로그인한 화면의 이메일 정보를 조회
        Long orderId;


        try{
            orderId = orderService.order(orderDto, email);
            //화면으로 부터 넘어오는 주문 정보와 회원의 이메일 정보를 이용하여 주문 로직을 호출
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
        //결과값으로 생성된 주문 번호와 요청이 성공했다는 Http 응답 상태 코드를 반환

    }
```

#### Service
```java
public Long order(OrderDto orderDto, String email){
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);
        //현재 로그인한 회원의 이메일 정보를 이용하여 회원정보 조회
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        //주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        //회원정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티를 생성
        orderRepository.save(order);
        return order.getId();
    }
```

<br>

## 📖 장바구니 기능
![장바구니 기능](https://user-images.githubusercontent.com/110580350/210511420-5c60c224-3ec0-485f-a2d5-acf8b4c2e7f5.gif)


#### Controller
```java
 @PostMapping("/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> filedErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError : filedErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        //현재 로그인한 회원의 이메일 정보를 변수에 저장
        String email = principal.getName();
        Long cartItemId;

        try{
            cartItemId = cartService.addCart(cartItemDto, email);
            //값으로 넘어온 장바구니에 담을 상품 정보와 현재 로그인한 회원의 이메일 정보를 이용하여 장바구니에 상품을 담는 로직을 호출
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
        //결과값으로 생성된 장바구니 상품 아이디와 요청이 성공하였다는 Http 응답 상태 코드 반환
    }
```

#### Service
```java
 public Long addCart(CartItemDto cartItemDto, String email){
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        //장바구니에 담을 Item Entity 조회
        Member member = memberRepository.findByEmail(email);
        //현재 로그인한 Member Entity 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        //현재 로그인한 회원의 Cart Entity 조회
        if(cart == null){ //상품을 처음으로 장바구니에 담을 경우 해당 회원의 Cart Entity 생성
            cart = Cart.creatCart(member);
            cartRepository.save(cart);
        }
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        //현재 상품이 장바구니에 이미 들어가 있는 지 조회
        if(savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
            //장바구니에 이미 있던 상품일 경우 기존 수량에 현재 장바구니에 담을 수량만큼 더함
        }else{
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            //Cart Entity, Item Entity, Count 를 이용하여 CartItem Entity 생성
            cartItemRepository.save(cartItem); //장바구니 저장
            return cartItem.getId();
        }
    } 
```

<br>

## 📖 상품 취소 기능
![상품 취소](https://user-images.githubusercontent.com/110580350/210512144-438c0b2d-dcb3-4b5c-998e-e420f8929399.gif)

#### Controller
```java
@PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cacelOrder(@PathVariable("orderId") Long orderId, Principal principal){
        if(!orderService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권환이 없습니다.",HttpStatus.FORBIDDEN);
        }
        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId,HttpStatus.OK);
    }

    @DeleteMapping("/orderItem/{orderItemId}")
    public @ResponseBody ResponseEntity deleteOrderItem(@PathVariable("orderItemId") Long orderItemId, Principal principal){

        if(!orderService.validateOrder(orderItemId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권환이 없습니다.",HttpStatus.FORBIDDEN);
        }

        orderService.deleteOrderItem(orderItemId);
        return new ResponseEntity<Long>(orderItemId, HttpStatus.OK);
    }
```
#### Service
```java
 public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    } //주문 취소 상태로 변경하면 변경감지 기능에 의해서 트렌젝션이 끝날 때 update 쿼리 실행
    
  public void deleteOrderItem(Long orderItemId){ //취소된 주문내역 삭제
      Order order = orderRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
      orderRepository.delete(order);
  }
  
  <div class="ml-3"> <!--주문 취소상태일 경우 주문내역 삭제 버튼 추가-->
                <th:block th:unless="${order.orderStatus == T(Spring.Project.status.OrderStatus).ORDER}">
                    <button type="button" class="btn btn-outline-danger" th:value="${order.orderId}"
                            th:data-id="${order.orderId}" onclick="deleteOrders(this)">주문내역 삭제</button>
                </th:block>
            </div>
```

<br>









