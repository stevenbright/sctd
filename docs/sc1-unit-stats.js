
// see also http://classic.battle.net/scc/protoss/pstats.shtml

var terran = [
//  0                  1    2      3      4    5    6      7        8     9             10            11            12            13        14
//  NAME               SIZE SUPPLY MINRLS GAS  ARMR HP   GR.ATK  AIR ATK  COOL         RANGE       ATTACK MOD     SIGHT           NOTES  BLD.TIME
  ["Battlecruiser",       "L",  6,  400,  300,  3,  500,  "25",   "25",   "30",         "6",          "3",          "11",         "SA",     160],
  ["Dropship",            "L",  2,  100,  100,  1,  150,  "0",    "0",    "-",          "0",          "0",          "8",          "T",      50],
  ["Firebat",             "S",  1,  50,   25,   1,  50,   "16cs", "0",    "22/11 Stim", "2",          "2",          "7",          "B",      24],
  // Goliath 11upg to Sight available only in Broodwar
  ["Ghost",               "S",  1,  25,   75,   0,  45,   "10c",  "10c",  "22",         "7",          "1",          "9/11 upg",   "B, SA",  50],
  ["Goliath",             "L",  2,  100,  50,   1,  125,  "12",   "20e",  "22",         "5/8 Air upg","1Gnd/4Air",  "8",          "",       40],
  ["Marine",              "S",  1,  50,   0,    0,  40,   "6",    "6",    "15/7.5Stim", "4/5 upg.",   "1",          "7",          "B",      24],
  ["Missile Turret",      "L",  0,  75,   0,    0,  200,  "0",    "20e",  "15",         "7",          "0",          "11",         "D",      30],
  ["Science Vessel",      "L",  2,  100,  225,  1,  200,  "0",    "0",    "-",          "0",          "0",          "10",         "D,SA",   80],
  ["SCV",                 "S",  1,  50,   0,    0,  60,   "5",    "0",    "15",         "1",          "0",          "7",          "B,W",    20],
  ["Siege Tank - Siege",  "L",  2,  150,  100,  1,  150,  "70es", "0",    "75",         "12",         "5",          "10",         "",       50],
  ["Siege Tank - Tank",   "L",  2,  150,  100,  1,  150,  "30e",  "0",    "37",         "7",          "3",          "10",         "",       50],
  ["Vulture",             "M",  2,  75,   0,    0,  80,   "20c",  "0",    "30",         "5",          "2",          "8",          "SA",     30],
  ["Wraith",              "L",  2,  150,  100,  0,  120,  "8",    "20e",  "30Gnd/22Air","5",          "1Gnd/2Air",  "7",          "SA",     60],
  // Broodwar-only units:
  ["Medic",               "S",  1,  50,   25,   1,  60,   "0",    "0",    "-",          "0",          "0",          "9",          "B,SA",   30],
  ["Valkyrie",            "L",  3,  250,  125,  2,  200,  "0",    "6es",  "64",         "6",          "1",          "8",          "",       60]
];

var zerg = [
  ["Broodling",           "S", 0,   0,    0,    "0", "30", "4", "0", "15", "1", "1", "5", "B", "0"],
  ["Defiler",             "M", 2,   50,   150,  "1", "80", "0", "0", " ", "0", "0", "10", "B,SA", "50"],
  ["Drone",               "S", 1,   50,   0,    "0", "40", "5", "0", "22", "1", "0", "7", "B,W", "20"],
  ["Egg",                 "L", 0,   0,    0,    "10", "200", "0", "0", " ", "0", "0", "4", " ", "0"],
  ["Guardian",            "L", 2,   150,  200,  "2", "150", "20", "0", "30", "8", "2", "11", "B", "40"],
  ["Hydralisk",           "M", 1,   75,   25,   "0", "80", "10e", "10e", "15", "4/5 upg.", "1", "6", "B", "28"],
  ["Infested Terran",     "S", 1,   100,  50,   "0", "60", "500es", "0", " ", "1", "0", "5", "B", "40"],
  ["Larva",               "S", 0,   0,    0,    "10", "25", "0", "0", " ", "0", "0", "4", " ", "0"],
  ["Mutalisk",            "S", 2,   100,  100,  "0", "120", "9", "9", "30", "3", "1", "7", "B", "40"],
  ["Overlord",            "L", 0,   100,  0,    "0", "200", "0", "0", " ", "0", "0", "9/11 upg.", "D,B,T", "40"],
  ["Queen",               "M", 2,   100,  100,  "0", "120", "0", "0", " ", "0", "0", "10", "B,SA", "50"],
  ["Scourge",             "S", 0.5, 12,   38,   "0", "25", "0", "110", " ", "1", "0", "5", "B", "30"],
  ["Spore Colony",        "L", 0,   175,  0,    "0", "400", "0", "15", "15", "7", "0", "10", "D", "20"],
  ["Sunken Colony",       "L", 0,   175,  0,    "2", "300", "40e", "0", "32", "7", "0", "10", " ", "20"],
  ["Ultralisk",           "L", 4,   200,  200,  "1/3 upg.", "400", "20", "0", "15", "1", "3", "7", "B", "60"],
  ["Zergling",            "S", 0.5, 25,   0,    "0", "35", "5", "0", "8/6 upg.", "1", "1", "5", "B", "28"],
  // Broodwar-only units:
  ["Devourer",            "L", 2,   250,  150,  "2", "250", "0", "25e", "100", "6", "2", "10", "B", "40"],
  ["Lurker",              "M", 2,   200,  200,  "1", "125", "20s", "0", "37", "6", "2", "8", "B", "40"]
];

var protoss = [
  ["Arbiter", "L", "4", "100", "350", "1", "200", "150", "10e", "10e", "45", "5", "1", "9", "SA", "160"],
  ["Archon", "L", "4", "100", "300", "0", "10", "350", "30s", "30s", "20", "2", "3", "8", " ", "20"],
  ["Carrier", "L", "6", "350", "250", "4", "300", "150", "6", "6", " ", "8", "1", "11", " ", "140"],
  ["Dragoon", "L", "2", "125", "50", "1", "100", "80", "20e", "20e", "30", "4/6"],
  ["upg.", "2", "8", " ", "40"],
  ["High Templar", "S", "2", "50", "150", "0", "40", "40", "0", "0", " ", "0", "0", "7", "B,SA", "50"],
  ["Observer", "S", "1", "25", "75", "0", "40", "20", "0", "0", " ", "0", "0", "9/11"],
  ["upg.", "D", "40"],
  ["Photon Cannon", "L", "0", "150", "0", "0", "100", "100", "20", "20", "22", "7", "0", "11", "D", "50"],
  ["Probe", "S", "1", "50", "0", "0", "20", "20", "5", "0", "22", "1", "0", "8", "W", "20"],
  ["Reaver", "L", "4", "200", "100", "0", "100", "80", "100s/125s upg.", "0", "60", "8", "0", "10", " ", "70"],
  ["Scout", "L", "3", "275", "125", "0", "150", "100", "8", "28e", "30Gnd/22Air", "4", "1Gnd/2Air", "8/10upg.", " ", "80"],
  ["Shuttle", "L", "2", "200", "0", "1", "80", "60", "0", "0", " ", "0", "0", "8", "T", "60"],
  ["Zealot", "S", "2", "100", "0", "1", "100", "60", "16", "0", "22", "1", "2", "7", "B", "40"],
  ["Corsair", "M", "2", "150", "100", "1", "100", "80", "0", "5es", "8", "5", "1", "9", "SA", "40"],
  // Broodwar-only units:
  ["Dark Archon", "L", "4", "250", "200", "1", "25", "200", "0", "0", " ", "0", "0", "10", "SA", "20"],
  ["Dark Templar", "S", "2", "125", "100", "1", "80", "40", "40", "0", "30", "1", "3", "7", "B", "50"]
];

/*
Cool is the time between the ending of the attack and the beginning of another.
Attack Mod is the Damage modifier for each level of upgrade.
Gnd is Ground attack for units with split Air/Ground Weapon Systems
Air is Air attack for units with split Air/Ground Weapon Systems
Upg is a stat change after getting an upgrade 

Red indicates that the information is specific to StarCraft Brood War.

Towers such as the Missile Turret have a detection range of 7. Mobile detectors such as the Science Vessel have a detection range of 11. This is independent of sight range. 

Attacks:
E indicates Explosive Attack (50% damage to Small Units 75% damage to Medium Units, full damage to Large Units).
C indicates Concussive/Plasma damage Attack (50% damage to Medium Units 25% dam to Large Units).
S indicates Splash damage, which affects all units in the blast area. 

Notes:

B = Biological
D = Unit can Detect cloaked/burrowed units
SA = Unit has one or more Special Abilities (is a spell-caster)
T = Transport
W = Worker
 */

module.exports.stats = {
  terran: terran
};


// var s = require('./sc1-unit-stats.js')
//
