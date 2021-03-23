const asciiXDots = 2, asciiYDots = 4;
let threshold = 127, asciiWidth = 100, asciiHeight;
let image;

var exam_script = {
    plus_num: function(){
        try{
            var result = 2 * 2
            Dot.getDoubleNum(result)
            var base64 = Dot.bitmapToBase64()
        }catch(err){
            console.log(">> [exam_script.plus_num()]" + err)
        }
    }
}
