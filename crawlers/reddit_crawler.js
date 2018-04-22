const puppeteer = require('puppeteer');
const readline = require('readline-sync');

let scrape = async (subreddits) => {
  // Actual Scraping goes Here...
  let data = []
  const browser = await puppeteer.launch({args: ['--no-sandbox']})
  const page = await browser.newPage()
  for (var sub of subreddits) {
          await page.goto('https://www.reddit.com/r/' + sub)
          await page.waitFor(2000)
          const result = await page.evaluate(() => {
                  let threads = []
                  let titles = document.querySelectorAll('a.title.may-blank')
                  let upvotes = document.querySelectorAll('div.score.unvoted')
                  for (var i = 0, len = titles.length; i < len; i++) {
                    let tmp_title = titles[i].innerText
                    let tmp_upvote = upvotes[i].getAttribute('title')
                    if(parseInt(tmp_upvote) >= 5000) threads.push([tmp_title, tmp_upvote])
                  }
                  return threads

          });
          data.push(result)
  }

  /*const result = await page.evaluate(() => {
    // return something
    let title = document.querySelector('#content_inner > article > div.row > div.col-sm-6.product_main > h1').innerText
    let upvotes = document.querySelector('#content_inner > article > div.row > div.col-sm-6.product_main > p.price_color').innerText
    return {title, price}
});*/
  // Scrape
  browser.close()
  // Return a value
  return data
};

var subreddits = readline.question("Enter subreddits: ");
console.log(subreddits);
subreddits = subreddits.split(';')

scrape(subreddits).then((value) => {
    console.log(value); // Success!
});
