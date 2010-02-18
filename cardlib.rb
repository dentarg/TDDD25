require 'socket'

# http://www.ida.liu.se/~TDDD25/labs/lab2_1/protocols.shtml

class Card
  def initialize(title,filename,url,thumb_url,x,y,width,height)
    @title     = title    
    @filename  = filename 
    @url       = url      
    @thumb_url = thumb_url
    @x         = x        
    @y         = y            
    @width     = width    
    @height    = height
  end
  
  def title
    @title
  end
  
  def filename
    @filename
  end
  
  def url
    @url
  end
  
  def x
    @x
  end
  
  def y
    @y
  end
  
  def width
    @width
  end
  
  def height
    @height
  end
  
  def write_message(message)
    w = CardWriteServer.new
    puts title
    w.write(filename)
    w.write("foo#{rand(1337)}.jpg")
    w.write("#{x} #{y} #{width} #{height}")
    w.write(message)
    w.write("end-of-message")
    url_to_img_with_msg = w.read.chop
    w.close
    return url_to_img_with_msg
  end  
end

class CardDatabaseServer
  def initialize
    host = "mir41.ida.liu.se"
    port = 7000
    @socket = TCPSocket.new(host, port)
  end
  
  def write(command)
    @socket.puts(command + "\n")
  end
  
  def read
    @socket.gets
  end  
  
  def close
    write("exit")
    @socket.close
  end
  
  def getCardsNumber
    write("getCardsNumber")
    read.to_i
  end
  
  def getCard(index)
    write("getCardInfo #{index}\n")
    title     = read.chop
    filename  = read.chop
    url       = read.chop
    thumb_url = read.chop
    x         = read.chop
    y         = read.chop    
    width     = read.chop
    height    = read.chop
    Card.new(title,filename,url,thumb_url,x,y,width,height)
  end
end

class CardWriteServer
  def initialize
    host = "sen8.ida.liu.se"
    port = 9000
    @socket = TCPSocket.new(host, port)
  end
  
  def write(command)
    @socket.puts(command + "\n")
  end
  
  def read
    @socket.gets
  end
  
  def close
    write("exit")
    @socket.close
  end
    
  def write_message(card, message)
    write(card.filename)
    write("foo#{rand(1337)}.jpg")
    write("#{card.x} #{card.y} #{card.width} #{card.height}")
    write(message)
    write("end-of-message")
    read.chop
  end
end

def testCardWriteServer
  s = CardDatabaseServer.new
  card = s.getCard(12)
  puts card.title
  puts card.write_message("LALALA")
  s.close
end

def testCardDatabaseServer
  s = CardDatabaseServer.new
  for i in 1..s.getCardsNumber
    card = s.getCard(i)
    puts card.title
  end
  s.close
end

#testCardDatabaseServer
#testCardWriteServer