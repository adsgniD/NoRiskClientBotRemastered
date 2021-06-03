import requests
from PIL import Image
import random
import sys

username = sys.argv[-1]

templates = ["elements/1.png", "elements/2.png", "elements/3.png", "elements/4.png", "elements/5.png", "elements/6.png", "elements/7.png", "elements/8.png", "elements/9.png"]

uuidRequestJson = requests.get('https://api.mojang.com/users/profiles/minecraft/' + username).json()
uuid = uuidRequestJson.get('id')

if uuid:
    renderUrl = "https://crafatar.com/renders/body/" + str(uuid) + "?overlay"
    r = requests.get(renderUrl)
    open('skinrender.png', 'wb').write(r.content)
    Image1 = Image.open(random.choice(templates))
    Image1copy = Image1.copy()
    Image2 = Image.open('skinrender.png')
    Image2copy = Image2.copy()
    Image2copy.thumbnail((80,128))
    Image1copy.paste(Image2copy, (19, 8), Image2copy)
    Image1copy.save('out.png')

elif not uuid:
    print('uuid is not valid')
    print('ERROR')
    print('...')
    print('und nein mooz ich hab kein obsi für dich..')