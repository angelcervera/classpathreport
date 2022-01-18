# Google Cloud Functions

## Report

```csv
functions-framework,NO_VERSION
```

## Command

```shell
mvn clean package
gcloud functions deploy csv-report \
    --entry-point=com.acervera.classpathreport.googlecloud.functions.ReportHandler \
    --runtime=java11 \
    --trigger-http \
    --source=runners/googlecloud/functions/target/deployment
```


