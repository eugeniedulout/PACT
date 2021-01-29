import java.awt.Image;

public interface BarCodeReader {
	public int readBarCode(Image barCode);
	/*Returns the product's ID given its bar code
	 *  Used by Map Bloc*/
}
