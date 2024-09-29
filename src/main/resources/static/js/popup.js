// 로컬 스토리지 관리 모듈
const StorageManager = {
  setItem: function(key, value, expirationDays) {
    const expirationMs = expirationDays * 24 * 60 * 60 * 1000;
    const item = {
      value: value,
      expiry: Date.now() + expirationMs
    };
    localStorage.setItem(key, JSON.stringify(item));
  },

  getItem: function(key) {
    const itemStr = localStorage.getItem(key);
    if (!itemStr) return null;

    const item = JSON.parse(itemStr);
    if (Date.now() > item.expiry) {
      localStorage.removeItem(key);
      return null;
    }
    return item.value;
  }
};

// 팝업 관리 모듈
const PopupManager = {
  popupSelector: '.main_popup',
  closeBtnSelector: '.btn_close',
  noShowCheckboxSelector: '#no-show-again',
  storageKey: 'popup-no-show',

  init: function() {
    this.popup = document.querySelector(this.popupSelector);
    this.closeBtn = document.querySelector(this.closeBtnSelector);
    this.noShowCheckbox = document.querySelector(this.noShowCheckboxSelector);

    this.setupEventListeners();
    this.checkAndShowPopup();
  },

  setupEventListeners: function() {
    this.closeBtn.addEventListener('click', () => this.closePopup());
  },

  checkAndShowPopup: function() {
    const noShow = StorageManager.getItem(this.storageKey);
    this.popup.style.display = noShow ? 'none' : 'block';
  },

  closePopup: function() {
    if (this.noShowCheckbox.checked) {
      StorageManager.setItem(this.storageKey, true, 1); // 1일 동안 보이지 않음
    }
    this.popup.style.display = 'none';
  }
};

// 초기화
document.addEventListener('DOMContentLoaded', () => PopupManager.init());