const asciiXDots = 2, asciiYDots = 4;
let threshold = 127, asciiWidth = 100, asciiHeight;
let image;

var bitmap = {
    get_bit_map: function() {
             try{
             Dot.getDoubleNum(4)
                bitmap = Dot.bitmapToBase64();
                                alert(bitmap);
            }catch(err){
                console.log(">> processing.gitbitmap()] " + err)
            }
    }
}