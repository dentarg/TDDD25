require 'socket'

# http://www.ida.liu.se/~TDDD25/labs/lab2_1/protocols.shtml

class Card <
  
  Struct.new(:title, :filename, :url, :thumb_url, :x, :y, :width, :height)
  
  def write_message(message)
    w = CardWriteServer.new
    w.write(filename)
    w.write("foo#{rand(1337)}.jpg")
    w.write("#{x} #{y} #{width} #{height}")
    w.write(message)
    w.write("end-of-message")
    url_to_img_with_msg = w.read
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
    @socket.gets.chop
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
    title     = read
    filename  = read
    url       = read
    thumb_url = read
    x         = read
    y         = read
    width     = read
    height    = read
    Card.new(title,filename,url,thumb_url,x,y,width,height)
  end

  def getAllCards
    cards = []
    for i in 1..getCardsNumber
       cards << getCard(i)
    end
    cards
  end

  def getCardByFilename(filename)
    getAllCards.each do |card|
      if card.filename == filename
        return card
      end
    end
    return nil
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
    @socket.gets.chop
  end
  
  def close
    write("exit")
    @socket.close
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
  s.getAllCards.each do |card|
    puts card.title
  end
  s.close
end

#testCardDatabaseServer
#testCardWriteServer