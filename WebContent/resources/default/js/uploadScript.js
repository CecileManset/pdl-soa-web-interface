
    function start() {  
        this.progressInterval = setInterval(function(){  
            pbClient.setValue(pbClient.getValue() + 10);  
        }, 2000);  
    }  
  
    function cancel() {  
        clearInterval(this.progressInterval);  
        pbClient.setValue(0);  
    }  
