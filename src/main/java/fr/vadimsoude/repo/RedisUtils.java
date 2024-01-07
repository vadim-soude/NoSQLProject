package fr.vadimsoude.repo;

import fr.vadimsoude.entity.Product;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RedisUtils {

    public static CopyOnWriteArrayList<String> getUpdatedCacheContent(){
        try{
            JedisPool pool = new JedisPool("localhost", 6379);
            Jedis jedis = pool.getResource();
            String data = jedis.get("cache-list");
            if(data == null || data.isEmpty() || data.equals("[]")){
                //cache-list is empty
                jedis.disconnect();
                pool.close();
                return new CopyOnWriteArrayList<>();
            }else{
                String replace = data.replace("[","");
                String replace1 = replace.replace("]","");
                if(replace1.isEmpty()){
                    jedis.disconnect();
                    pool.close();
                    return new CopyOnWriteArrayList<>();
                }
                ArrayList<String> tempList = new ArrayList<>(Arrays.asList(replace1.split(", ")));
                CopyOnWriteArrayList<String> cacheContent = new CopyOnWriteArrayList<>(tempList);
                if(!cacheContent.isEmpty()){
                    for (String product:cacheContent) {
                        if(product != null){
                            if(jedis.get(product) == null){
                                cacheContent.remove(product);
                            }
                        }
                    }
                }
                jedis.set("cache-list",cacheContent.toString());
                jedis.disconnect();
                pool.close();
                return cacheContent;
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static List<Product> getProductFromCachedSearch(String search){
        List<Product> products = new ArrayList<>();
        try{
            JedisPool pool = new JedisPool("localhost", 6379);
            Jedis jedis = pool.getResource();
            String data = jedis.get(search);
            if(data == null || data.isEmpty() || data.equals("[]")){
                //cache-list is empty
                jedis.disconnect();
                pool.close();
                return new ArrayList<>();
            }else{
                String replace = data.replace("[","");
                String replace1 = replace.replace("]","");
                ArrayList<String> cacheContent = new ArrayList<>(Arrays.asList(replace1.split(",")));
                if(!cacheContent.isEmpty()){
                    for (String product:cacheContent) {
                        String name = Arrays.asList(product.split("\\|")).get(0);
                        double price = Double.parseDouble(Arrays.asList(product.split("\\|")).get(1));
                        Integer quantity = Integer.parseInt(Arrays.asList(product.split("\\|")).get(2));
                        products.add(new Product(name,price,quantity));
                    }
                }
                jedis.disconnect();
                pool.close();
                return products;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void addSearchToCache(String name, List<Product> products){
        CopyOnWriteArrayList<String> cacheContent = getUpdatedCacheContent();
        cacheContent.add(name);
        try{
            JedisPool pool = new JedisPool("localhost", 6379);
            Jedis jedis = pool.getResource();
            jedis.set("cache-list",cacheContent.toString());
            ArrayList<String> dataForCache = new ArrayList<>();
            for (Product product:products) {
                dataForCache.add(product.toString());
            }
            jedis.set(name,dataForCache.toString());
            jedis.expire(name,3600L);
            jedis.disconnect();
            pool.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public static void updateCache(String name){
        CopyOnWriteArrayList<String> cacheContent = getUpdatedCacheContent();
        try{
            JedisPool pool = new JedisPool("localhost", 6379);
            Jedis jedis = pool.getResource();

            for (String search:cacheContent) {
                if(name.contains(search)){
                    cacheContent.remove(search);
                    jedis.expire(search,1L);
                }
            }

            jedis.set("cache-list",cacheContent.toString());
            jedis.disconnect();
            pool.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }

    }


}
