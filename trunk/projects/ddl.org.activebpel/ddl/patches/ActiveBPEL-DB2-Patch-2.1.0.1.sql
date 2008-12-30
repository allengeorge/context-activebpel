CONNECT TO ABPEL USER bpeluser;

DROP INDEX AeProcessPlanId;

UPDATE AeMetaInfo SET PropertyValue = '2.1.0.1' WHERE PropertyName = 'Version';
