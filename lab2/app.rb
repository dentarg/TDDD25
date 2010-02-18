require 'rubygems'
require 'sinatra'
require 'cardlib.rb'

get '/' do
  server = CardDatabaseServer.new
  @cards = server.getAllCards
  server.close
  erb :index
end

get '/:filename' do
  server = CardDatabaseServer.new
  @card = server.getCardByFilename(params[:filename])
  server.close
  erb :card
end

post '/write_message' do
  server = CardDatabaseServer.new
  card = server.getCardByFilename(params[:filename])
  server.close
  if card
    img_url = card.write_message(params[:msg])
    "<p><img src='#{img_url}' /></p> \n
    <p><a href='/#{card.filename}'>Back to image</a> |
    <a href='/'>Back to index</a></p>"
  else
    "Card not found"
  end
end