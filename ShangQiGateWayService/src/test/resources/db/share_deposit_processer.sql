DELETE FROM "SVC_MNG"."CP_TRADE_ITEM_TEMP" WHERE id =2000001;
UPDATE "SVC_MNG"."CP_TRADE_ITEM_TEMP" SET STATUS = '1'  WHERE TRADE_TYPE='0010' AND DIRECTION='-1';
delete from CP_TRADE_ITEM_DEALED where COUPON_NO = '00000000000001';
delete from cp_deposit_order where  coupon_no = '00000000000001';
delete from CP_VIRTUAL_CARD where COUPON_NO = '00000000000001';