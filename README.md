MVVM with Kotlin

API: https://jsonplaceholder.typicode.com, end point: /photos

-  三級緩存
- 100% Kotlin
- LiveData
- Retrofit(with Coroutine)
- Navigation Component
- View Binding
- Dependency injection with Hilt   (branch hilt_add)



題目：

第一個頁面只需要有一個 Button 換場到下一個頁面。

第二個頁面要把 API 的內容呈現，需要呈現的項目有三個,分別是 “id”, “title”,“thumbnailUrl”，
Id 和 title 是 String，thumbnailUrl 是圖案的網址,要在 cell 內的 imageView 中呈現每一行放四個格子。

第三個頁面是點擊了第二個頁面後的任一格，推入下一頁，要呈現當格的背景圖、id、和 title

UI 要能自動適應各尺寸大小的螢幕。

禁止使用第三方套件
例如:
Glide(com.github.bumptech.glide)
Picasso(com.squareup.picasso)

可以使用的library
andriod或androidx等官方library
kotlin coroutine 
Gson、Moshi等Json套件，當然最好使用原生JsonArray和JsonObject．
