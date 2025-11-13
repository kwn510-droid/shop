package dto;

public class Cart {
	private int cartCode;
	private int goodsCode;       // GOODS_CODE
    private int customerCode;    // CUSTOMER_CODE
    private int cartQuantity;    // CART_QUANTITY
    private String createdate;   // CREATEDATE (문자열로 받기)

    // getter / setter
    public int getGoodsCode() {
        return goodsCode;
    }
    public void setGoodsCode(int goodsCode) {
        this.goodsCode = goodsCode;
    }

    public int getCustomerCode() {
        return customerCode;
    }
    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }
    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCreatedate() {
        return createdate;
    }
    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
    public int getCartCode() {
		return cartCode;
	}
	public void setCartCode(int cartCode) {
		this.cartCode = cartCode;
	}
	@Override
	public String toString() {
		return "Cart [cartCode=" + cartCode + ", goodsCode=" + goodsCode + ", customerCode=" + customerCode
				+ ", cartQuantity=" + cartQuantity + ", createdate=" + createdate + "]";
	}


}