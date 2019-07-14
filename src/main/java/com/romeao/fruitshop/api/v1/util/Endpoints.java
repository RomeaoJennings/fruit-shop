package com.romeao.fruitshop.api.v1.util;

public class Endpoints {
    private static final String API_BASE = "/api/v1";

    private static String concatenate(String base, String subPath) {
        return base + "/" + subPath;
    }

    public static class Customers {
        public static final String URL = API_BASE + "/customers";

        public static String byCustomerIdUrl(Long id) {
            return concatenate(URL, id.toString());
        }
    }


    public static class Categories {
        public static final String URL = API_BASE + "/categories";

        public static String byCategoryNameUrl(String name) {
            return concatenate(URL, name);
        }
    }


    public static class Vendors {
        public static final String URL = API_BASE + "/vendors";

        public static String byVendorIdUrl(Long id) {
            return concatenate(URL, id.toString());
        }
    }

    public static class Products {
        public static final String URL = API_BASE + "/products";

        public static String byProductIdUrl(Long productId) {
            return concatenate(URL, productId.toString());
        }
    }
}
