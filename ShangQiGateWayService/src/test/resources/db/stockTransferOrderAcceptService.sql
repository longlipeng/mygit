DELETE FROM SVC_MNG.TB_SELL_ORDER_CARD_LIST WHERE ORDER_CARD_LIST_ID='600580' and ORDER_ID= '1554';
DELETE FROM SVC_MNG.TB_ENTITY_STOCK WHERE CARD_NO = '2100000000000217253';
INSERT INTO SVC_MNG.TB_SELL_ORDER_CARD_LIST(ORDER_CARD_LIST_ID, ORDER_ID, CARDHOLDER_ID, LAST_NAME, FIRST_NAME, EXTERNAL_ID, CARD_NO, CARD_PIN_STATE, CARD_STATE, LEG_CARD_NO, CREDIT_AMOUNT, CREATE_USER, CREATE_TIME, MODIFY_USER, MODIFY_TIME, DATA_STATE, ORDER_LIST_ID)
  VALUES('600580', '1554', NULL, NULL, NULL, NULL, '2100000000000217253', '2', '2', NULL, NULL, '1', '20130926093546', '1', '20130926093546', '1', NULL);
  
INSERT INTO SVC_MNG.TB_ENTITY_STOCK(CARD_NO, ENTITY_ID, FUNCTION_ROLE_ID, PRODUCT_ID, CARD_LAYOUT_ID, FACE_VALUE_TYPE, FACE_VALUE, CARD_VALID_DATE, STOCK_STATE, CREATE_USER, CREATE_TIME, MODIFY_USER, MODIFY_TIME, DATA_STATE, IS_USED, FLD_01_RES_DATA, FLD_02_RES_DATA)
  VALUES('2100000000000217253', '5101', '2', '1006', '1001', '0', '100000', '20991231', '3', '1', '20130926103606', '1', '20130926103606', '1', '0', NULL, NULL);
  
  
