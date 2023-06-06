let Target = document.getElementById("clock");
function clock() {
  let time = new Date();

  let month = time.getMonth();
  let date = time.getDate();
  let day = time.getDay();
  let week = ['일', '월', '화', '수', '목', '금', '토'];

  let hours = time.getHours();
  let minutes = time.getMinutes();
  let seconds = time.getSeconds();

  Target.innerText = '현재 : '+
      `${month + 1}/${date}(${week[day]}) ` +
      `${hours < 10 ? `0${hours}` : hours}:${minutes < 10 ? `0${minutes}` : minutes}:${seconds < 10 ? `0${seconds}` : seconds}`;

}
clock();
setInterval(clock, 1000); // 1초마다 실행