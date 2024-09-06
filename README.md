GeoServicePipeline projesi üç aşamadan oluşmaktadır:

GeonamesMicroService: İlk aşamada, Java ve Spring kullanılarak geliştirilmiş bir mikro hizmettir. Bu mikro hizmet, Geonames API'den çoğrafi veriyi çekip hazır hale getirir.

WikiService: İkinci aşamada, Kotlin dilinde geliştirilmiş bir Android servisi olan WikiService bulunur. Bu servis, Retrofit kullanarak GeonamesMicroService'ten veri alır.

WikiClient: Üçüncü aşamada, Kotlin dilinde geliştirilmiş bir uygulama olan WikiClient bulunur. Bu uygulama, Messenger kullanarak WikiService'ten veri alır ve ekrana basar.
