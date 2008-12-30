USE ActiveBpel;

DROP INDEX AeProcessByPlanId ON AeProcess;

UPDATE AeMetaInfo SET PropertyValue = '2.1.0.1' WHERE PropertyName = 'Version'; 
