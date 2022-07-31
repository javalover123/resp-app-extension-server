# DefaultController

All URIs are relative to `""`

The controller class is defined in **[DefaultController.java](../../src/main/java/org/javalover123/resp/api/DefaultController.java)**

Method | HTTP request | Description
------------- | ------------- | -------------
[**dataFormattersGet**](#dataFormattersGet) | **GET** /data-formatters | 
[**dataFormattersIdDecodePost**](#dataFormattersIdDecodePost) | **POST** /data-formatters/{id}/decode | 
[**dataFormattersIdEncodePost**](#dataFormattersIdEncodePost) | **POST** /data-formatters/{id}/encode | 

<a name="dataFormattersGet"></a>
# **dataFormattersGet**
```java
Mono<List<DataFormatter>> DefaultController.dataFormattersGet()
```



Returns a list of data formatters


### Return type
[**List&lt;DataFormatter&gt;**](../../docs/models/DataFormatter.md)

### Authorization
* **basic**

### HTTP request headers
 - **Accepts Content-Type**: Not defined
 - **Produces Content-Type**: `application/json`

<a name="dataFormattersIdDecodePost"></a>
# **dataFormattersIdDecodePost**
```java
Mono<String> DefaultController.dataFormattersIdDecodePost(iddecodePayload)
```



### Parameters
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
**id** | `String` | The id of data formatter |
**decodePayload** | [**DecodePayload**](../../docs/models/DecodePayload.md) |  | [optional parameter]

### Return type
`String`

### Authorization
* **basic**

### HTTP request headers
 - **Accepts Content-Type**: `application/json`
 - **Produces Content-Type**: `application/json`

<a name="dataFormattersIdEncodePost"></a>
# **dataFormattersIdEncodePost**
```java
Mono<String> DefaultController.dataFormattersIdEncodePost(idencodePayload)
```



### Parameters
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
**id** | `String` | The id of data formatter |
**encodePayload** | [**EncodePayload**](../../docs/models/EncodePayload.md) |  | [optional parameter]

### Return type
`String`

### Authorization
* **basic**

### HTTP request headers
 - **Accepts Content-Type**: `application/json`
 - **Produces Content-Type**: `application/json`

