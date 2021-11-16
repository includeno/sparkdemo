# Spark问题解决



### org.apache.spark.SparkException: Task not serializable

实体未序列化
org.apache.spark.SparkException: Task not serializable

解决方法
implements Serializable



Gson gson=new Gson();不可序列化 因此每一次操作都需要新对象



### Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/spark/sql/SparkSession



解决方法

maven中去掉scope

<scope>provided</scope>





### Caused by: java.lang.IllegalStateException: SparkSession should only be created and accessed on the driver.

SparkSession写在了业务中

解决方法

SparkSession全局唯一



Caused by: java.lang.IllegalStateException: SparkSession should only be created and accessed on the driver.