# android readability web content <!-- omit in toc -->

It's a project provides a function which likes some browser readability page.

## API

* open reader view
`ReaderJSInterface.renderReadabilityPage(webView, url)`
  
* custom style
You can custom your style without any JS inject.
`ReaderJSInterface.exeJavaScript(webView, json)`
    -  custom style `{"action":"setColorScheme","colorScheme":"light|dark|sepia|heti"}`
    -  custom font size `{"action":"changeFontSize","fontSize":1 | -1}  1:increase -1:decrease`
    -  custom font type `{"action":"setFontType","fontType":"sans-serif|serif"}`

* samples

https://user-images.githubusercontent.com/3881604/179922522-98c8dec8-3241-4b9f-a54c-8640f6451237.mp4

## Build & Develop

### Android

open android dir with Android Studio

### Web

* first init submodule
```shell
git submodule update --init --force
```
* web dir is reader page source code, you can use custom reader style in it.
- cd web
- sh pre-build.sh
- sh build.sh

## Roadmap

- [ ] support GeckoView, I have to write a WebExtension to avoid the CORS problem.
- [ ] support iOS WKWebView.

## License

```
                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
